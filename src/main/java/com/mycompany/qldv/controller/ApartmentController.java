package com.mycompany.qldv.controller;

import com.mycompany.qldv.model.Apartment;
import com.mycompany.qldv.model.ServiceFee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ApartmentController {
    private final String apartmentFile = "src/main/resources/data/apartments.json";
    private final String serviceFeeFile = "src/main/resources/data/serviceFees.json";
    private List<Apartment> apartments = new ArrayList<>();
    private List<ServiceFee> serviceFees = new ArrayList<>();

    public ApartmentController() {
        loadApartments();
        loadServiceFees();
    }

    public void addApartment(Apartment apartment) {
        apartments.add(apartment);
        saveApartments();
    }

    public void addServiceFee(ServiceFee serviceFee) {
        serviceFees.add(serviceFee);
        saveServiceFees();
    }

    public List<Apartment> searchApartments(String keyword) {
        List<Apartment> result = new ArrayList<>();
        for (Apartment apartment : apartments) {
            if (apartment.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                apartment.getAddress().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(apartment);
            }
        }
        return result;
    }

    public double getTotalFees() {
        return serviceFees.stream().mapToDouble(ServiceFee::getAmount).sum();
    }

    public List<ServiceFee> getServiceFeesSorted() {
        List<ServiceFee> sortedFees = new ArrayList<>(serviceFees);
        sortedFees.sort(Comparator.comparingDouble(ServiceFee::getAmount));
        return sortedFees;
    }

    private void loadApartments() {
        try (FileReader reader = new FileReader(apartmentFile)) {
            Type listType = new TypeToken<List<Apartment>>() {}.getType();
            apartments = new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadServiceFees() {
        try (FileReader reader = new FileReader(serviceFeeFile)) {
            Type listType = new TypeToken<List<ServiceFee>>() {}.getType();
            serviceFees = new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveApartments() {
        try (FileWriter writer = new FileWriter(apartmentFile)) {
            new Gson().toJson(apartments, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveServiceFees() {
        try (FileWriter writer = new FileWriter(serviceFeeFile)) {
            new Gson().toJson(serviceFees, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public List<ServiceFee> getServiceFees() {
        return serviceFees;
    }
}
