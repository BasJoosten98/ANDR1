package com.example.fhictcompanion.News;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class NewsPost implements Parcelable, ITaskReceiver {
    private int REQUESTCODE_DOWNLOAD_IMAGE = 2;

    private String title;
    private String author;
    private String content;
    private String link;
    private byte[] image;
    private String imageURL;

    public NewsPost(String title, String author, String content, String link, Bitmap image, String imageURL) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.link = link;
        this.image = getBytes(image);
        this.imageURL = imageURL;
    }

    protected NewsPost(Parcel in) {
        title = in.readString();
        author = in.readString();
        content = in.readString();
        link = in.readString();
        imageURL = in.readString();
        //image = in.createByteArray();
        //image = new byte[in.readInt()];
        //in.readByteArray(image);
    }

    public static final Creator<NewsPost> CREATOR = new Creator<NewsPost>() {
        @Override
        public NewsPost createFromParcel(Parcel in) {
            return new NewsPost(in);
        }

        @Override
        public NewsPost[] newArray(int size) {
            return new NewsPost[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(link);
        dest.writeString(imageURL);
        /*
        if(image == null) {
            dest.writeInt(0);
            dest.writeByteArray(image);
        }
        else {
            dest.writeInt(image.length);
            dest.writeByteArray(image);
        }
         */
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public void OnTaskReceived(Object data, int requestCode) {
        if(REQUESTCODE_DOWNLOAD_IMAGE == requestCode){
            if(data != null){
                if(data instanceof Bitmap){
                    Bitmap img = (Bitmap) data;
                    image = getBytes(img);
                }
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public Bitmap getImage() {
        if(image == null){return null;}
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bitmap;
    }

    public void DownloadImage(){
        if(imageURL != null){
            new JSONTaskBitmap(this, REQUESTCODE_DOWNLOAD_IMAGE).execute(imageURL);
        }
    }

    private byte[] getBytes(Bitmap bitmap){
        if(bitmap == null){return  null;}
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return b;
    }
}
