package org.example;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

public class AuditInsertListener implements PreInsertEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        System.out.println("Saving entity: " + event.getEntity().toString());
        return false;
    }

    private void setField(Object[] state, String[] names, String field, Object value) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(field)) {
                state[i] = value;
                break;
            }
        }
    }
}