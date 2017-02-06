package my.edu.unikl.android.common.util;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;

import my.edu.unikl.gotravel.R;
import my.edu.unikl.gotravel.model.rome2rio.IndicativePrice;
import my.edu.unikl.gotravel.model.rome2rio.Segment;


public class Util {

    public static final DecimalFormat formatter = new DecimalFormat("#0");

    public static final int MINUTES_IN_WEEK = 10800;
    public static final int DAYS_IN_WEEK = 7;

    public static String getDuration(int duration){

        StringBuffer result = new StringBuffer();

        int days = duration/(24 * 60);

        int hours = (duration % (24 * 60)) / 60;

        int minutes = (duration % (24 * 60)) % 60;

        if (days > 0)
            result.append(days).append(" d ");
        if (hours > 0)
            result.append(hours).append(" hrs ");
        if (minutes > 0)
            result.append(minutes).append(" min");


        return result.toString();

    }

    public static String getFrequency(int frequencyInWeek){

        StringBuffer result = new StringBuffer();


        double frequency = Math.round(MINUTES_IN_WEEK / frequencyInWeek);

        if (frequencyInWeek <= 180){

            frequency = Math.round(frequencyInWeek / DAYS_IN_WEEK);

            result.append(getNumberFormat(frequency)).append(" a day");

        } else {

            result.append("Every ").append(getNumberFormat(frequency)).append(" min");
        }


        return result.toString();

    }

    public static String getNumberFormat(double price){

        DecimalFormat formatter = new DecimalFormat("#0");

        return formatter.format(price);
    }

    public static String getPriceFormat(IndicativePrice indicativePrice){

        StringBuffer price = new StringBuffer("");

        if (indicativePrice != null) {

            if (indicativePrice.getPrice() > 0) {

                price.append(indicativePrice.getCurrency()).append(" ").append(formatter.format(indicativePrice.getPrice()));
            }

            if (indicativePrice.getNativePrice() > 0) {

                price.append("\u2248").append(indicativePrice.getNativeCurrency()).append(" ").append(formatter.format(indicativePrice.getNativePrice()));
            }


        }

        return price.toString();
    }

    public static int getTransportIcon(String kind) {

        if (kind == null)
            return R.drawable.destination;

        int icon;

        switch (kind) {
            case "train":
                icon = R.drawable.train2;
                break;
            case "subway":
                icon = R.drawable.train;
                break;
            case "flight":
                icon = R.drawable.plane;
                break;
            case "tram":
                icon = R.drawable.tram;
                break;
            case "cable_car":
                icon = R.drawable.cable_car;
                break;
            case "car":
                icon = R.drawable.car;
                break;
            case "ferry":
                icon = R.drawable.ferry;
                break;
            case "bus":
                icon = R.drawable.bus;
                break;
            case "walk":
                icon = R.drawable.walk;
                break;
            case "taxi":
                icon = R.drawable.taxi;
                break;
            case "horse":
                icon = R.drawable.horse;
                break;
            case "carferry" :
                icon = R.drawable.car_ferry;
                break;
            default:
                icon = R.drawable.destination;
               // throw new IllegalArgumentException("Invalid kind: ");
        }
        return icon;

    }

    public static int getMarkerIcon(String kind) {

        if (kind == null)
            return R.drawable.destination;

        int icon;

        switch (kind) {
            case "train":
                icon = R.drawable.pin2;
                break;
            case "subway":
                icon = R.drawable.pin3;
                break;
            case "flight":
                icon = R.drawable.pin4;
                break;
            case "tram":
                icon = R.drawable.pin5;
                break;
            case "cable_car":
                icon = R.drawable.pin1;
                break;
            case "car":
                icon = R.drawable.pin6;
                break;
            case "ferry":
                icon = R.drawable.pin7;
                break;
            case "bus":
                icon = R.drawable.pin8;
                break;
            case "walk":
                icon = R.drawable.pin9;
                break;
            case "taxi":
                icon = R.drawable.pin10;
                break;
            case "horse":
                icon = R.drawable.pin2;
                break;
            case "carferry" :
                icon = R.drawable.pin3;
                break;
            default:
                icon = R.drawable.pin1;
        }
        return icon;

    }

    public static int getPoligonColor(String kind) {

        int color;

        int alpha = 190;

        switch (kind) {
            case "train":
                color = Color.argb(alpha, 128,0,0);
                break;
            case "subway":
                color = Color.argb(alpha, 221,160,221);
                break;
            case "flight":
                color = Color.argb(alpha, 255,127,80);
                break;
            case "tram":
                color = Color.argb(alpha, 255,127,80);
                break;
            case "cable_car":
                color = Color.argb(alpha, 255,182,193);
                break;
            case "car":
                color = Color.argb(alpha, 240,128,128);
                break;
            case "ferry":
                color = Color.argb(alpha, 139,0,139);
                break;
            case "bus":
                color = Color.argb(alpha, 154,205,50);
                break;
            case "walk":
                color = Color.argb(alpha, 255,127,80);
                break;
            case "taxi":
                color = Color.argb(alpha, 255,215,0);
                break;
            case "horse":
                color = Color.argb(alpha, 255,127,80);
                break;
            case "carferry" :
                color = Color.argb(alpha, 255,20,147);
                break;
            default:
                color = Color.argb(alpha, 135,206,250);
                // throw new IllegalArgumentException("Invalid kind: ");
        }
        return color;

    }

    public static String convertToHref(String value, String url) {

        StringBuffer result = new StringBuffer("<a href='");

        result.append(url).append("'>").append(value).append("</a>");

        return result.toString();


    }

    public static void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }


    public static String getKind(Segment segment) {

        String kind = segment.getKind();

        if (segment.getSubkind() != null) {
            if (!segment.getKind().equals(segment.getSubkind())){
                if (segment.getSubkind().equals("unknown"))
                    kind = segment.getKind();
                else
                    kind = segment.getSubkind();

            } else{
                kind = segment.getKind();
            }
        }

        return kind;
    }

    public static int getPlaceMarkerIcon(String type) {

        if (type == null)
            return R.drawable.destination;

        int icon;

        switch (type) {
            case "airport":
                icon = R.drawable.airport;
                break;
            case "amusement_park":
            case "park":
                icon = R.drawable.themepark;
                break;
            case "aquarium":
                icon = R.drawable.aquarium;
                break;
            case "art_gallery":
                icon = R.drawable.artgallery;
                break;
            case "atm":
                icon = R.drawable.atm_2;
                break;
            case "bakery":
                icon = R.drawable.bread;
                break;
            case "bank":
            case "finance":
                icon = R.drawable.bank;
                break;
            case "bar":
                icon = R.drawable.bar;
                break;
            case "beauty_salon":
            case "hair_care":
                icon = R.drawable.beautysalon;
                break;
            case "book_store":
                icon = R.drawable.highschool;
                break;
            case "bowling_alley":
                icon = R.drawable.bowling;
                break;
            case "bus_station":
                icon = R.drawable.busstop;
                break;
            case "campground":
                icon = R.drawable.summercamp;
                break;
            case "car_rental":
                icon = R.drawable.carrental;
                break;
            case "casino":
                icon = R.drawable.casino;
                break;
            case "cafe":
                icon = R.drawable.cafetaria;
                break;
            case "church":
                icon = R.drawable.church_2;
                break;
            case "city_hall":
                icon = R.drawable.citysquare;
                break;
            case "dentist":
                icon = R.drawable.dentist;
                break;
            case "department_store":
            case "convenience_store":
            case "clothing_store":
            case "store":
                icon = R.drawable.departmentstore;
                break;
            case "embassy":
                icon = R.drawable.embassy;
                break;
            case "food":
                icon = R.drawable.fastfood;
                break;
            case "health":
            case "gym":
                icon = R.drawable.fitness;
                break;
            case "home_goods_store":
                icon = R.drawable.homecenter;
                break;
            case "establishment":
            case "point_of_interest":
                icon = R.drawable.historicalquarter;
                break;
            case "gas_station":
                icon = R.drawable.fillingstation;
                break;
            case "doctor":
            case "hospital":
                icon = R.drawable.hospital_building;
                break;
            case "hindu_temple":
                icon = R.drawable.temple_2;
                break;
            case "grocery_or_supermarket":
                icon = R.drawable.supermarket;
                break;
            case "library":
                icon = R.drawable.library;
                break;
            case "liquor_store":
                icon = R.drawable.liquor;
                break;
            case "locality":
                icon = R.drawable.administrativeboundary;
                break;
            case "local_government_office":
                icon = R.drawable.bigcity;
                break;
            case "locksmith":
                icon = R.drawable.lock;
                break;
            case "lodging":
                icon = R.drawable.lodging;
                break;
            case "meal_delivery":
                icon = R.drawable.fooddeliveryservice;
                break;
            case "meal_takeaway":
                icon = R.drawable.takeaway;
                break;
            case "mosque":
                icon = R.drawable.mosquee;
                break;
            case "movie_rental":
                icon = R.drawable.movierental;
                break;
            case "movie_theater":
                icon = R.drawable.theater;
                break;
            case "museum":
                icon = R.drawable.museum_art;
                break;
            case "natural_feature":
                icon = R.drawable.forest2;
                break;
            case "night_club":
                icon = R.drawable.stripclub2;
                break;
            case "parking":
                icon = R.drawable.parkinggarage;
                break;
            case "pharmacy":
                icon = R.drawable.medicalstore;
                break;
            case "police":
                icon = R.drawable.police;
                break;
            case "political":
                icon = R.drawable.political;
                break;
            case "post_box":
            case "post_office":
                icon = R.drawable.postal;
                break;
            case "restaurant":
                icon = R.drawable.restaurant;
                break;
            case "school":
                icon = R.drawable.school;
                break;
            case "shopping_mall":
                icon = R.drawable.mall;
                break;
            case "transit_station":
            case "subway_station":
                icon = R.drawable.underground;
                break;
            case "spa":
                icon = R.drawable.spa;
                break;
            case "stadium":
                icon = R.drawable.stadium;
                break;
            case "taxi_stand":
                icon = R.drawable.taxiway;
                break;
            case "train_station":
                icon = R.drawable.train_station;
                break;
            case "real_estate_agency":
            case "travel_agency":
                icon = R.drawable.travel_agency;
                break;
            case "university":
                icon = R.drawable.university;
                break;
            case "zoo":
                icon = R.drawable.zoo;
                break;

            default:
                icon = R.drawable.administration;
        }
        return icon;

    }

}
