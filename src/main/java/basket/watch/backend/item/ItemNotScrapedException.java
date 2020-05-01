package basket.watch.backend.item;

public class ItemNotScrapedException extends RuntimeException {
    public ItemNotScrapedException(String message) {
        super(message);
    }
}
