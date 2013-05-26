package uk.org.djce.countdown.resultsformatter;

import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Map;

public final class GenericCaptors {

    private GenericCaptors () {
        throw new AssertionError();
    }

    /**
     * This is really icky.
     * http://code.google.com/p/mockito/issues/detail?id=161
     * http://code.google.com/p/mockito/issues/detail?id=137
     */
    @SuppressWarnings("unchecked")
    public static <K, V> ArgumentCaptor<Map<K, V>> mapCapture () {
        return (ArgumentCaptor<Map<K, V>>) (Object) ArgumentCaptor.forClass(Map.class);
    }

    @SuppressWarnings("unchecked")
    public static <V> ArgumentCaptor<List<V>> listCapture () {
        return (ArgumentCaptor<List<V>>) (Object) ArgumentCaptor.forClass(List.class);
    }

}
