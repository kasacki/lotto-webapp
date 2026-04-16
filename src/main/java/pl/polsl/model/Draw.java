package pl.polsl.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Immutable record representing a single lotto draw.
 */
public record Draw(LocalDate date, List<Integer> numbers) { }
