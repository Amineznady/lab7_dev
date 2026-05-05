package com.example.starsgallery.service;

import com.example.starsgallery.beans.Star;
import com.example.starsgallery.dao.Idao;
import java.util.ArrayList;
import java.util.List;

public class StarService implements Idao<Star> {
    private List<Star> stars;
    private static StarService instance;

    private StarService() {
        stars = new ArrayList<>();
        seed();
    }

    public static StarService getInstance() {
        if (instance == null) instance = new StarService();
        return instance;
    }

    private void seed() {
        stars.add(new Star("Kate Bosworth", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_W_f07f_P-H-08vF-02-F5-H8-F5-H8-F5-H8-F5-H8&s", 3.0f));
        stars.add(new Star("George Clooney", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_W_f07f_P-H-08vF-02-F5-H8-F5-H8-F5-H8-F5-H8&s", 3.0f));
        stars.add(new Star("Michelle Rodriguez", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_W_f07f_P-H-08vF-02-F5-H8-F5-H8-F5-H8-F5-H8&s", 4.0f));
        stars.add(new Star("George Clooney", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_W_f07f_P-H-08vF-02-F5-H8-F5-H8-F5-H8-F5-H8&s", 3.0f));
    }

    @Override public boolean create(Star o) { return stars.add(o); }
    @Override public boolean update(Star o) {
        for (Star s : stars) {
            if (s.getId() == o.getId()) {
                s.setName(o.getName());
                s.setImg(o.getImg());
                s.setRating(o.getRating());
                return true;
            }
        }
        return false;
    }
    @Override public boolean delete(Star o) { return stars.remove(o); }
    @Override public Star findById(int id) {
        for (Star s : stars) if (s.getId() == id) return s;
        return null;
    }
    @Override public List<Star> findAll() { return stars; }
}