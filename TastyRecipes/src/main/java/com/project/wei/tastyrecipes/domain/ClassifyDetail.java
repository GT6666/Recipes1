package com.project.wei.tastyrecipes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wei on 2016/9/2 0002.
 */
public class ClassifyDetail {

    public String resultcode;
    public String reason;


    public ResultBean result;
    public int error_code;

    public static class ResultBean {
        public String totalNum;
        public String rn;
        public String pn;
        public List<DataBean> data;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "totalNum='" + totalNum + '\'' +
                    ", rn='" + rn + '\'' +
                    ", pn='" + pn + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
    public static class StepsBean implements Parcelable{
        public String img;
        public String step;

        protected StepsBean(Parcel in) {
            img = in.readString();
            step = in.readString();
        }

        public static final Creator<StepsBean> CREATOR = new Creator<StepsBean>() {
            @Override
            public StepsBean createFromParcel(Parcel in) {
                return new StepsBean(in);
            }

            @Override
            public StepsBean[] newArray(int size) {
                return new StepsBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(img);
            dest.writeString(step);
        }
    }

        public static class DataBean implements Parcelable {
            public String id;
            public String title;
            public List<String> albums;
            public List<StepsBean> steps;
            public String burden;
            public String imtro;
            public String ingredients;


            protected DataBean(Parcel in) {
                id = in.readString();
                title = in.readString();
                albums = in.createStringArrayList();
                steps = in.createTypedArrayList(StepsBean.CREATOR);
                burden = in.readString();
                imtro = in.readString();
                ingredients = in.readString();
            }

            public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
                @Override
                public DataBean createFromParcel(Parcel in) {
                    return new DataBean(in);
                }

                @Override
                public DataBean[] newArray(int size) {
                    return new DataBean[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(title);
                dest.writeStringList(albums);
                dest.writeTypedList(steps);
                dest.writeString(burden);
                dest.writeString(imtro);
                dest.writeString(ingredients);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<String> getAlbums() {
                return albums;
            }

            public void setAlbums(List<String> albums) {
                this.albums = albums;
            }

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            public String getBurden() {
                return burden;
            }

            public void setBurden(String burden) {
                this.burden = burden;
            }

            public String getImtro() {
                return imtro;
            }

            public void setImtro(String imtro) {
                this.imtro = imtro;
            }

            public String getIngredients() {
                return ingredients;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public static Creator<DataBean> getCREATOR() {
                return CREATOR;
            }
        }









}
