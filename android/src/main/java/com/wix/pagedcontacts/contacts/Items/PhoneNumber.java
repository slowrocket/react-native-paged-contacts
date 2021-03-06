package com.wix.pagedcontacts.contacts.Items;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.facebook.react.bridge.WritableMap;
import com.wix.pagedcontacts.contacts.query.QueryParams;

class PhoneNumber extends ContactItem {
    public String type;
    private String number;

    PhoneNumber(Cursor cursor) {
        super(cursor);
        fillFromCursor();
    }

    private void fillFromCursor() {
        String number = getString(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String normalizedNumber = getString(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER);
        this.number = number != null ? number : normalizedNumber;

        Integer type = getInt(ContactsContract.CommonDataKinds.Phone.TYPE);
        this.type = getType(type);
    }

    private String getType(Integer type) {
        if (type == null) {
            throw new InvalidCursorTypeException();
        }
        switch (type) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                return "home";
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                return "work";
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                return "mobile";
            default:
                return "other";
        }
    }

    @Override
    protected void fillMap(WritableMap map, QueryParams params) {
        addToMap(map, "label", type);
        addToMap(map, "value", number);
    }
}
