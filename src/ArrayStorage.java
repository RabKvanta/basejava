import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
       int i = 0;
       while ( storage[i] != null ) {
                    storage[i++] = null;
        }
    }

    void save(Resume r) {
        if ( r != null && this.size() < storage.length )
                                storage[this.size()] = r;
    }

    Resume get(String uuid) {
        for  ( int i = 0; i < this.size(); i++ ) {
           if ( storage[i].uuid.equals(uuid) )
                           return  storage[i];
        }

        return null;
    }

    void delete(String uuid) {
        for  ( int i = 0 ; i < this.size(); i++) {
            if ( storage[i].uuid.equals(uuid) ) {
                for  ( ; i < this.size()-1; i++) {
                    storage[i] = storage[i+1];
                }
                storage[i] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
            return Arrays.copyOf(storage, this.size() );
    }

    int size() {
        int size = 0;
        for  ( ; size < storage.length; size++) {
            if ( storage[size] == null )
                                    break;
        }
        return size;
    }
}
