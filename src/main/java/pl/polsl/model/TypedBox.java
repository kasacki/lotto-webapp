/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

/**
 *
 * @author KaSac
 */


/**
 * Generic type-safe wrapper for values.
 * @param <T>
 */
public class TypedBox<T> {
    private final T value;

    public TypedBox(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}

