package com.example.clinton.companion.todayword;


public class Examples {
        String text = "";
        String title = "";

        public static Examples newInstance()
        {
            return new Examples();
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
}
