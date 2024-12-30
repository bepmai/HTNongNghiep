package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Component.Helper.TimeHelper;
import tlu.edu.vn.ht63.htnongnghiep.Model.Notification;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class notification_listNotification_adapter extends RecyclerView.Adapter<notification_listNotification_adapter.notification_view_holder> {
    private List<Notification> notifcationList;

    public notification_listNotification_adapter(List<Notification> notifcationList){
        this.notifcationList = notifcationList;
    }

    @NonNull
    @Override
    public notification_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_notifcation_content,parent,false);
        return new notification_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notification_view_holder holder, int position) {
        Notification notification = this.notifcationList.get(position);

        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateFormatted = currentDate.format(formatter);
        String timestamp = notification.getTime();
        try {
            TimeHelper timeHelper = new TimeHelper(timestamp,currentDateFormatted,"yyyy-MM-dd HH:mm:ss");
            String distanceTime = timeHelper.getTimeDifferenceDisplay();
            holder.time.setText(distanceTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Uri imageUri = Uri.parse(notification.getUri());
        Glide.with(holder.imageContent.getContext())
                .load(imageUri)
                .into(holder.imageContent);

        String titleContent= notification.getTitleContent();
        String contentMess = notification.getContentMess();
        String combinedText = titleContent + " " + contentMess;
        SpannableString spannableString = new SpannableString(combinedText);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, titleContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.content.setText(spannableString);

        holder.nofiContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.notifcationList.size();
    }

    public static class notification_view_holder extends RecyclerView.ViewHolder {
        private ImageView imageContent;
        private View nofiContainer;
        private TextView content, time;
        public notification_view_holder(@NonNull View itemView) {
            super(itemView);
            this.imageContent = itemView.findViewById(R.id.imageContent);
            this.content = itemView.findViewById(R.id.content);
            this.time = itemView.findViewById(R.id.time);
            this.nofiContainer = itemView.findViewById(R.id.nofiContainer);
        }
    }
}
