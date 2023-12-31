package ar.edu.itba.paw.models.assetLendingContext.implementations;

public enum AssetState {
    PUBLIC(){
        @Override
        public boolean isPublic() {
            return true;
        }
    },
    PRIVATE(){
        @Override
        public boolean isPrivate() {
            return true;
        }

    },
    BORROWED(){
        @Override
        public boolean isBorrowed() {
            return true;
        }
    },
    PENDING() {
        @Override
        public boolean isPending() {return true; }

    },
    DELAYED() {
        @Override
        public boolean isDelayed() { return true;}

    },
    DELETED() {
        @Override
        public boolean isDeleted() {
            return true;
        }
    };


    public boolean isPublic() { return false;}
    public boolean isBorrowed() { return false;}
    public boolean isPrivate() { return false; }

    public boolean isPending() {return false; }

    public boolean isDelayed() { return false;}

    public boolean isDeleted() { return false;}

    public static AssetState fromString(String value) {
        if (value != null) {
            for (AssetState condition : AssetState.values()) {
                if (value.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
