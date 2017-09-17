package id.web.androidhyper.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by wildhan on 8/27/2017 in BakingApp.
 * Keep Spirit!!
 */

public class IngredientModel extends RealmObject implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public IngredientModel() {
    }

    public IngredientModel(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected IngredientModel(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientModel> CREATOR = new Creator<IngredientModel>() {
        @Override
        public IngredientModel createFromParcel(Parcel in) {
            return new IngredientModel(in);
        }

        @Override
        public IngredientModel[] newArray(int size) {
            return new IngredientModel[size];
        }
    };

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

    @Override
    public String toString() {
        return "IngredientModel{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }
}

