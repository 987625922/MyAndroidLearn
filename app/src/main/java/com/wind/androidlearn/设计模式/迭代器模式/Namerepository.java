package com.wind.androidlearn.设计模式.迭代器模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 16:07
 */
class Namerepository implements Container {

    public String names[] = {"Robert", "John", "Lora"};

    @Override
    public Iterator getIterator() {
        return new NameIterator();
    }

    private class NameIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            if (index < names.length) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return names[index++];
            }
            return null;
        }
    }
}
