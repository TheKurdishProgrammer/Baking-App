package com.example.mohammed.baking_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class IngredientsBean implements Parcelable {
    public static final Creator<IngredientsBean> CREATOR = new Creator<IngredientsBean>() {
        @Override
        public IngredientsBean createFromParcel(Parcel in) {
            return new IngredientsBean(in);
        }

        @Override
        public IngredientsBean[] newArray(int size) {
            return new IngredientsBean[size];
        }
    };
    /**
     * quantity : 2
     * measure : CUP
     * ingredient : Graham Cracker crumbs
     */

    @SerializedName("quantity")
    private double quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    private IngredientsBean(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}
