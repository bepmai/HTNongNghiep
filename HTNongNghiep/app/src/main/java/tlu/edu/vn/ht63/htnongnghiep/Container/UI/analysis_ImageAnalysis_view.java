package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;

import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.DiaglogEvent;
import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.OnToastDismissListener;
import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.TreeCallBack;
import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.DialogFragment;
import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.ToastFragment;
import tlu.edu.vn.ht63.htnongnghiep.Model.TreeLib;
import tlu.edu.vn.ht63.htnongnghiep.R;

import androidx.camera.view.PreviewView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class analysis_ImageAnalysis_view extends Fragment {
    private static final String API_KEY = "pS6zAsWb7VeYLegrJi49JyvWR648agvOen2NaENabVet4e9S0n";
    private static final String API_URL_IDENTITY = "https://plant.id/api/v3/identification";
    private static final String API_URL_HEALTH = "https://plant.id/api/v3//api/v3/health_assessment?details=local_name,description,url,treatment,classification,common_names,cause";
    private static final String TAG = "CameraX";
    private PreviewView previewView;
    private ProcessCameraProvider cameraProvider;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageCapture imageCapture;
    private ImageButton listImage;

    private int state = CameraSelector.LENS_FACING_BACK;
    private int flashMode = ImageCapture.FLASH_MODE_OFF;

    private ExecutorService cameraExecutor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static analysis_ImageAnalysis_view newInstance(String param1, String param2) {
        analysis_ImageAnalysis_view fragment = new analysis_ImageAnalysis_view();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // Sử dụng ActivityResultContracts để yêu cầu quyền camera
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if (isGranted) {
                        startCamera();
                    } else {
                        Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == -1 && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();  // Lấy URI của ảnh đã chọn
                    if (selectedImageUri != null) {
                        listImage.setImageURI(selectedImageUri); // Đặt ảnh vào ImageButton
                        try {
                            File imageFile = getImageFileFromUri(selectedImageUri);
                            uploadImage(imageFile);
                        } catch (IOException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

    public analysis_ImageAnalysis_view() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Camera", "CreateView");
        View view = inflater.inflate(R.layout.fragment_image_analysis_view, container, false);
        previewView = view.findViewById(R.id.previewView);
        // Kiểm tra quyền truy cập camera
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("Camera", "Check Camera");
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            startCamera();
        }
        ImageButton captureButton = view.findViewById(R.id.captureButton);
        captureButton.setOnClickListener(v -> takePicture());

        listImage = view.findViewById(R.id.listImage);
        listImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                pickImageLauncher.launch(intent);
            }
        });

        ImageButton changeCamera = view.findViewById(R.id.changeCamera);
        changeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == CameraSelector.LENS_FACING_BACK) {
                    state = CameraSelector.LENS_FACING_FRONT;
                } else {
                    state = CameraSelector.LENS_FACING_BACK;
                }
                startCamera();
            }
        });

        ImageButton lighting = view.findViewById(R.id.lighting);
        lighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashMode == ImageCapture.FLASH_MODE_OFF) {
                    flashMode = ImageCapture.FLASH_MODE_ON;
                    lighting.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.lighting_on));
                } else {
                    flashMode = ImageCapture.FLASH_MODE_OFF;
                    lighting.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.lighting));
                }
                startCamera();
            }
        });

        ImageButton close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent home= new Intent(requireActivity(), HomeActivity.class);
//                startActivity(home);
                requireActivity().finish();
            }
        });
        loadLastImage(listImage);
        return view;
    }
    private File getImageFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
        if (inputStream != null) {
            File tempFile = createTempFile();
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
            return tempFile;  // Trả về file tạm
        } else {
            throw new IOException("Không thể đọc file từ URI.");
        }
    }
    private File createTempFile() throws IOException {
        File tempFile = File.createTempFile("upload", ".jpg",requireActivity().getCacheDir());
        tempFile.deleteOnExit();
        return tempFile;
    }

    private void loadLastImage(ImageButton listImage) {
        String[] projection = {
                MediaStore.Images.Media._ID, // Địa chỉ ID của ảnh
                MediaStore.Images.Media.DATE_ADDED // Ngày thêm ảnh
        };

        // Sắp xếp ảnh theo ngày được thêm vào (mới nhất ở đầu)
        String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";

        Uri imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Truy vấn ảnh với projection và sắp xếp
        try (Cursor cursor = requireContext().getContentResolver().query(imagesUri, projection, null, null, orderBy)) {
            if (cursor != null  && cursor.moveToFirst()) {
                // Lấy URI ảnh cuối cùng
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                Uri imageUri = Uri.withAppendedPath(imagesUri, String.valueOf(id));

                // Đặt ảnh vào ImageButton
                Glide.with(listImage).load(imageUri).into(listImage);
            }
        }
        catch (Exception e){
            Log.d("Error", String.valueOf(e));
        }
    }

    private void startCamera() {
        Log.d("Camera", "startCamera");
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(state)
                        .build();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                try {
                    imageCapture = new ImageCapture.Builder()
                            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                            .setFlashMode(flashMode)
                            .build();
                    cameraProvider.unbindAll();

                    // Bind tất cả các use cases vào lifecycle của Fragment
                    cameraProvider.bindToLifecycle(
                            this, // LifecycleOwner
                            cameraSelector,
                            preview,
                            imageCapture // BIND ImageCapture tại đây
                    );


                } catch (Exception e) {
                    Log.e(TAG, "Use case binding failed", e);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error starting camera: ", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private Rect getFrameLayoutBounds(View frameLayout) {
        // Khai báo một đối tượng Rect để lưu trữ tọa độ và kích thước của View
        int[] location = new int[2];  // Tọa độ của View trên màn hình
        frameLayout.getLocationOnScreen(location);

        // Lấy kích thước của FrameLayout
        int width = frameLayout.getWidth();
        int height = frameLayout.getHeight();

        // Tạo và trả về Rect dựa trên tọa độ và kích thước của View
        return new Rect(location[0], location[1], location[0] + width, location[1] + height);
    }


    private void takePicture() {
        Log.d(TAG, "takePicture: Start");
        View flasView = requireView().findViewById(R.id.flashView);
        createFlashEffect(flasView);
        if (imageCapture != null) {
            imageCapture.takePicture(ContextCompat.getMainExecutor(requireContext()), new ImageCapture.OnImageCapturedCallback() {
                @Override
                public void onCaptureSuccess(@NonNull ImageProxy image) {
                    Bitmap bitmap = imageProxyToBitmap(image);
                    image.close();

                    try {
                        View frameLayout = requireView().findViewById(R.id.frameLayout);
                        Rect cropBounds = getFrameLayoutBounds(frameLayout);
                        Bitmap croppedBitmap = cropImage(bitmap, cropBounds);
                        if (croppedBitmap != null) {
//                            saveImageToPicture(croppedBitmap);
//                            loadLastImage(listImage);
                            File file = saveBitmapToTempFile(croppedBitmap, requireContext());
                            DialogFragment.openDialogImage(requireContext(),Uri.parse(""));
                            uploadImage(file);
                        } else {
                            ToastFragment toastFragment = new ToastFragment(3, "Cắt ảnh không thành công!");
                            toastFragment.showToast(requireActivity().getSupportFragmentManager(), R.id.main);
                        }
                    } catch (Exception error) {
                        Log.d(TAG, "onCaptureSuccess: " + error.getMessage());
                    }
                }

                @Override
                public void onError(@NonNull ImageCaptureException exc) {
                    Log.e(TAG, "Photo capture failed: " + exc.getMessage());
                }

            });
        } else {
            Log.d(TAG, "takePicture: Null");
        }
    }

    private Bitmap imageProxyToBitmap(ImageProxy imageProxy) {
        ImageProxy.PlaneProxy[] planes = imageProxy.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer(); // Lấy buffer từ Plane đầu tiên
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    private void saveImageToPicture(Bitmap bitmap) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "cropped_image_" + System.currentTimeMillis() + ".png");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/farmTLU"); // Thư mục con "MyApp" trong Pictures

        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        if (imageUri != null) {
            try (OutputStream outputStream = contentResolver.openOutputStream(imageUri)) {
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    ToastFragment toastFragment = new ToastFragment(1, "Ảnh đã được lưu vào thư viện!");
                    toastFragment.showToast(requireActivity().getSupportFragmentManager(), R.id.main);
//                    toastFragment.showOverlayToast(requireContext());
                    Fragment analysisResultView = new analysis_AnalysisResult_view();
//                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.setCustomAnimations(
//                                R.anim.enter_from_bottom,
//                                R.anim.exit_to_bottom,
//                                R.anim.enter_from_bottom,
//                                R.anim.exit_to_bottom
//                        );
//                        fragmentTransaction.add(R.id.main, analysisResultView);
//                        fragmentTransaction.addToBackStack("resultTree");
//                        fragmentTransaction.commit();
//                    }, 2000);
//                    DialogFragment dg = new DialogFragment("Hello", new DiaglogEvent() {
//                        @Override
//                        public void setCloseButtonEvent() {
//                            DiaglogEvent.super.setCloseButtonEvent();
//                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                            fragmentManager.popBackStack();
//                        }
//
//                        @Override
//                        public void setSubmitButtonEvent() {
//                            DiaglogEvent.super.setSubmitButtonEvent();
//                        }
//                    });
//                    dg.openDialog(requireActivity().getSupportFragmentManager(), R.id.main);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error saving image to Pictures directory: ", e);
            }
        } else {
            Toast.makeText(requireContext(), "Không thể lưu ảnh!", Toast.LENGTH_SHORT).show();
        }
    }

    private void createFlashEffect(View flashView) {
        // Đảm bảo rằng flashView là View đen mà bạn đã tạo trong layout
        flashView.setVisibility(View.VISIBLE);  // Hiển thị View đen

        // Tạo hiệu ứng mờ dần của View đen
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(flashView, "alpha", 1f, 0f);
        fadeOut.setDuration(200); // Thời gian hiệu ứng (200ms)

        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                flashView.setVisibility(View.GONE);  // Ẩn lại View sau khi hiệu ứng hoàn thành
            }
        });

        fadeOut.start();  // Bắt đầu hiệu ứng
    }

    private Bitmap cropImage(Bitmap originalBitmap, Rect cropBounds) {
        // Tính tỷ lệ cho việc cắt ảnh
        float scaleX = (float) originalBitmap.getWidth() / getResources().getDisplayMetrics().widthPixels;
        float scaleY = (float) originalBitmap.getHeight() / getResources().getDisplayMetrics().heightPixels;

        // Chuyển đổi các tọa độ từ Rect sang kích thước thực tế trong ảnh
        int left = (int) (cropBounds.left * scaleX);
        int top = (int) (cropBounds.top * scaleY);
        int right = (int) (cropBounds.right * scaleX);
        int bottom = (int) (cropBounds.bottom * scaleY);

        // Cắt ảnh
        return Bitmap.createBitmap(originalBitmap, left, top, right - left, bottom - top);
    }

    private File saveBitmapToTempFile(Bitmap bitmap, Context context) throws Exception {
        File tempFile = File.createTempFile("temp_image", ".jpg", context.getCacheDir());
        FileOutputStream fos = new FileOutputStream(tempFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); // Nén ảnh thành JPEG
        fos.flush();
        fos.close();
        return tempFile;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cameraProvider != null) {
            cameraProvider.unbindAll(); // Unbind all use cases
        }

        // Log khi unbind camera
        Log.d(TAG, "Camera has been unbound.");
    }

    private void uploadImage(File file) throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        String encodedImage = Base64.getEncoder().encodeToString(fileContent);
        JSONArray imagesArray = new JSONArray();
        imagesArray.put(encodedImage); // Thêm chuỗi base64 vào mảng images

        // Tạo JSON payload
        JSONObject json = new JSONObject();
        json.put("images", imagesArray);
        json.put("latitude", 20.9944576);
        json.put("longitude", 105.8209792);
        json.put("similar_images", true);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                json.toString()
        );
        Request request = new Request.Builder()
                .url(API_URL_IDENTITY) // Địa chỉ API
                .addHeader("Content-Type", "application/json") // Thêm Content-Type header
                .addHeader("API-Key", API_KEY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // Xử lý lỗi
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("Response",responseBody);
                    JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                    JsonObject result = jsonResponse.getAsJsonObject().getAsJsonObject("result");
                    JsonArray suggestions = result.get("classification").getAsJsonObject().getAsJsonArray("suggestions");
                    final TreeLib tree = new TreeLib();
                    String id = suggestions.get(0).getAsJsonObject().get("id").getAsString();
                    String name = suggestions.get(0).getAsJsonObject().get("name").getAsString();


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plants");
                    databaseReference.orderByChild("unique").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Có cây trong thư viện
                                for (DataSnapshot snapshotd : snapshot.getChildren()) {
                                    TreeLib treeF = snapshotd.getValue(TreeLib.class);
                                    ToastFragment toast = new ToastFragment(1, "Đã thấy thông tin cây trong thư viện!");
                                    toast.showToast(requireActivity().getSupportFragmentManager(), R.id.main);
                                    toast.setOnToastDismissListener(new OnToastDismissListener() {
                                        @Override
                                        public void onToastDismiss() {
                                            tree.setName(treeF.getName());
                                            tree.setId(treeF.getId());
                                            tree.setImages(treeF.getImages());
                                            tree.setArea(treeF.getArea());
                                            tree.setDiscription(treeF.getDiscription());
                                            tree.setDistribution(treeF.getDistribution());
                                            tree.setEnviromentLive(treeF.getEnvironmentLive());
                                            tree.setFeature(treeF.getFeature());
                                            tree.setHeightMean(treeF.getHeightMean());
                                            tree.setSuns(treeF.getSuns());
                                            tree.setLifecycle(treeF.getLifecycle());
                                            tree.setMean(treeF.getMean());
                                            tree.setWaters(treeF.getWaters());
                                            tree.setTrunk(treeF.getTrunk());
                                            tree.setTemperature(treeF.getTemperature());
                                            tree.setUnique(treeF.getUnique());
                                        }
                                    });
                                }
                            } else {
                                // Không có cây trong thư viện
                                ToastFragment toast = new ToastFragment(2, "Không tìm thấy thông tin cây trong thư viện, hiển thị thông tin cây cơ bản!");
                                toast.showToast(requireActivity().getSupportFragmentManager(), R.id.main);
                                toast.setOnToastDismissListener(new OnToastDismissListener() {
                                    @Override
                                    public void onToastDismiss() {
                                        //
                                        tree.setId(id);
                                        tree.setName(name);
                                        List<String> arrImg = new ArrayList<>();
                                        JsonArray similarImages = suggestions.get(0).getAsJsonObject().get("similar_images").getAsJsonArray();
                                        for (int i = 0; i < similarImages.size(); i++) {
                                            JsonObject image = similarImages.get(i).getAsJsonObject();

                                            String url = image.get("url").getAsString();
                                            arrImg.add(url);
                                        }
                                        tree.setImages(arrImg);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Fragment analysisResultView = new analysis_AnalysisResult_view(tree);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(
                                R.anim.enter_from_bottom,
                                R.anim.exit_to_bottom,
                                R.anim.enter_from_bottom,
                                R.anim.exit_to_bottom
                        );
                        fragmentTransaction.add(R.id.main, analysisResultView);
                        fragmentTransaction.addToBackStack("resultTree");
                        fragmentTransaction.commit();
                    }, 2000);
                } else {
                    System.out.println("Upload thất bại: " + response.code());
                }
            }
        });
    }

}
