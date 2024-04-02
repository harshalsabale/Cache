package cache.storage;

import com.cache.storage.HashMapBasedStorage;
import com.cache.storage.Storage;
import com.cache.exceptions.NotFoundException;
import com.cache.exceptions.StorageFullException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HashMapBasedStorageTest {

    @Test
    void addMethodTest() {
        Storage<String, Integer> storage = new HashMapBasedStorage<>(5);
        storage.add("a", 1);
        storage.add("b", 1);
        assertEquals(Integer.valueOf(1), storage.get("a"));
        assertEquals(Integer.valueOf(1), storage.get("b"));
    }

    @Test
    void addMethodTest_ThrowsStorageFullException() {
        Storage<String, Integer> storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 1);
        storage.add("c", 1);
        assertThrows(StorageFullException.class, () -> {
            storage.add("d", 2);
        });
    }

    @Test
    void addMethodTest_whenSameKeyAdded() {
        Storage<String, Integer> storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 2);
        storage.add("c", 3);
        storage.add("a", 4);
        assertEquals(Integer.valueOf(4), storage.get("a"));
    }

    @Test
    void getMethodTest_ThrowNotFoundException() {
        Storage<String, Integer> storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 2);
        storage.add("c", 3);
        assertThrows(NotFoundException.class, () -> storage.get("d"));
    }

    @Test
    void removeMethodTest_ThrowNotFoundException() {
        Storage<String, Integer> storage = new HashMapBasedStorage<>(1);
        assertThrows(NotFoundException.class, () -> storage.remove("d"));
    }

    @Test
    void removeMethodTest_success() {
        Storage<String, Integer> storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 2);
        storage.add("c", 3);
        storage.remove("a");
        assertThrows(NotFoundException.class, () -> storage.get("a"));
    }

    @Test
    void getAllKeysMethodTest_success() {
        Storage<String, Integer> storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 2);
        storage.add("c", 3);
        List<String> expected = List.of("a","b","c");
        assertEquals(expected, storage.getAllKeys());
    }

    @Test
    void getAllKeysMethodTest_ThrowNotFoundException() {
        Storage<String, Integer> storage = new HashMapBasedStorage<>(3);
        assertThrows(NotFoundException.class, storage::getAllKeys);
    }

}
