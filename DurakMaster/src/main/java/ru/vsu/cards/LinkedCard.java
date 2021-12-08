package ru.vsu.cards;

public class LinkedCard {
        public Card card;
        public LinkedCard next;

        public LinkedCard(Card value, LinkedCard next) {
            this.card = value;
            this.next = next;
        }
        public LinkedCard(Card value) {
            this(value, null);
        }
        public LinkedCard() {
            this(null);
        }

        public void setCard(Card card) {
            this.card = card;
        }

        public void setNext(LinkedCard next) {
            this.next = next;
        }
}
