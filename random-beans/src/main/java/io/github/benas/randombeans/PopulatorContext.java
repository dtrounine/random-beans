/**
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package io.github.benas.randombeans;

import io.github.benas.randombeans.api.EnhancedRandom;

import java.lang.reflect.Field;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Context object for a single call on {@link EnhancedRandom#nextObject(Class, String...)}.
 * It contains a map acting as a cache of populated beans to avoid infinite recursion.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
class PopulatorContext {

    private final int maxRandomizationDepth;

    private final int maxObjectPoolSize;

    private final Set<String> excludedFields;

    private final Map<Class<?>, List<Object>> populatedBeans;

    private final Stack<PopulatorContextStackItem> stack;

    private final Random random;

    PopulatorContext(final long seed, final int maxObjectPoolSize, final int maxRandomizationDepth, final String... excludedFields) {
        random = new Random(seed);
        populatedBeans = new IdentityHashMap<>();
        stack = new Stack<>();
        this.maxObjectPoolSize = maxObjectPoolSize;
        this.maxRandomizationDepth = maxRandomizationDepth;
        this.excludedFields = new HashSet<>(toLowerCase(Arrays.asList(excludedFields)));
    }

    void addPopulatedBean(final Class<?> type, Object object) {
        List<Object> objects = populatedBeans.get(type);
        if (objects == null) {
            objects = new ArrayList<>(maxObjectPoolSize);
        }
        if (objects.size() < maxObjectPoolSize) {
            objects.add(object);
        }
        populatedBeans.put(type, objects);
    }

    Object getPopulatedBean(final Class<?> type) {
        int actualPoolSize = populatedBeans.get(type).size();
        int randomIndex = actualPoolSize > 1 ? nextInt(0, actualPoolSize) : 0;
        return populatedBeans.get(type).get(randomIndex);
    }

    boolean hasAlreadyRandomizedType(final Class<?> type) {
        return populatedBeans.containsKey(type) && populatedBeans.get(type).size() == maxObjectPoolSize;
    }

    Set<String> getExcludedFields() {
        return excludedFields;
    }

    void pushStackItem(final PopulatorContextStackItem field) {
        stack.push(field);
    }

    void popStackItem() {
        stack.pop();
    }

    String getFieldFullName(final Field field) {
        List<String> pathToField = getStackedFieldNames();
        pathToField.add(field.getName());
        return String.join(".", toLowerCase(pathToField));
    }

    boolean hasExceededRandomizationDepth() {
        int currentRandomizationDepth = stack.size();
        return currentRandomizationDepth > maxRandomizationDepth;
    }

    private List<String> getStackedFieldNames() {
        return stack.stream().map(i -> i.getField().getName()).collect(toList());
    }

    private List<String> toLowerCase(final List<String> strings) {
        return strings.stream().map(String::toLowerCase).collect(toList());
    }

    private int nextInt(int startInclusive, int endExclusive) {
        return startInclusive + random.nextInt(endExclusive - startInclusive);
    }
}
