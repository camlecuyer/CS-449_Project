package com.umkc.cj.ar_list;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest
{
    @Test
    public void testList() throws Exception {
        ListData data = new ListData();
        assertEquals("", data.getName());
        assertEquals(0, data.getNumber());
    }

    @Test
    public void testListFull() throws Exception {
        ListData data = new ListData("R", 3);
        assertEquals("R", data.getName());
        assertEquals(3, data.getNumber());
    }

    @Test
    public void testListItem() throws Exception {
        ListItemData item = new ListItemData();
        assertEquals("", item.getName());
        assertEquals(0, item.getNumber());
        assertEquals(0, item.getQuantity());
    }

    @Test
    public void testListItemFull() throws Exception {
        ListItemData item = new ListItemData("S", 1, 5);
        assertEquals("S", item.getName());
        assertEquals(1, item.getNumber());
        assertEquals(5, item.getQuantity());
    }
}