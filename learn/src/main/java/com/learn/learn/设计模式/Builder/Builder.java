package com.learn.learn.设计模式.Builder;

public class Builder {
    private boolean isLong;
    private boolean isThin;
    private boolean isBuild;

    public Builder(Build build) {
        isLong = build.isLong;
        isThin = build.isThin;
        isBuild = build.isBuild;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("Builder设计模式的使用:");

        if (isLong) {
            str.append(" 是长的");
        }
        if (isThin) {
            str.append(" 是瘦的");
        }
        if (isBuild) {
            str.append(" 是要创造的");
        }
        return str.toString();
    }

    public static class Build {

        private boolean isLong;
        private boolean isThin;
        private boolean isBuild;

        public Build() {

        }

        public Build writeIsLong() {
            isLong = true;
            return this;
        }

        public Build writeIsThin() {
            isThin = true;
            return this;
        }

        public Build writeIsBuild() {
            isBuild = true;
            return this;
        }

        public Builder getBuilder() {
            return new Builder(this);
        }
    }

}
