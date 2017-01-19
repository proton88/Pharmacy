package com.suglob.pharmacy.entity;

import java.util.Iterator;
import java.util.List;

public class ListDragTag extends Entity {
    private List<String> listDrugs;
    private Iterator<String> iterator;

    public ListDragTag(List<String> listDrugs) {
        this.listDrugs = listDrugs;
        iterator = listDrugs.iterator();
    }

    public int getSize() {
        return listDrugs.size();
    }

    public String getDrug() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public List<String> getListDrugs() {
        return listDrugs;
    }

    public void iter(){
        iterator=listDrugs.iterator();
    }
}
