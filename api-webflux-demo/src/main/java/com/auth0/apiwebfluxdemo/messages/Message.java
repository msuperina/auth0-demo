package com.auth0.apiwebfluxdemo.messages;

class Message {
    private String value;

    public Message() {}

    public Message(final String value) {
        this.value = value;
    }

    @SuppressWarnings("unused")
    public String getValue() {
        return value;
    }

    @SuppressWarnings("unused")
    public void setValue(final String value) {
        this.value = value;
    }
}
