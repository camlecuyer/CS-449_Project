package com.umkc.cj.ar_list;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class ApplicationTest
{
    private Data dt;

    @Before
    public void set()
    {
        dt = new Data(InstrumentationRegistry.getContext());
    }

    @Test
    public void testPreConditions()
    {
        Assert.assertNotNull(dt);
    }

    @Test
    public void testAddList() throws Exception
    {
        dt.addListWithValidation("R");
        List<ListData> lists = dt.getLists();

        Assert.assertEquals(lists.size(), 1);
        Assert.assertTrue(lists.get(0).getName().equals("R"));
    }

    @Test
    public void testAddItem() throws Exception
    {
        dt.addItemWithValidation("A", 3, 1);
        ArrayList<ListItemData> item = dt.getListItems(1);

        Assert.assertEquals(item.size(), 1);
        Assert.assertTrue(item.get(0).getName().equals("A"));
        Assert.assertEquals(item.get(0).getQuantity(), 3);

        testUpdateAndDeleteItem();
        testUpdateAndDeleteList();
    }

    public void testUpdateAndDeleteList() throws Exception
    {
        testUpdateList();
        testDeleteList();
    }

    public void testUpdateAndDeleteItem() throws Exception
    {
        testUpdateItem();
        testDeleteItem();
    }

    public void testUpdateList() throws Exception
    {
        dt.updateListWithValidation(1, "D");
        List<ListData> lists = dt.getLists();

        Assert.assertEquals(lists.size(), 1);
        Assert.assertTrue(lists.get(0).getName().equals("D"));
    }

    public void testUpdateItem() throws Exception
    {
        dt.updateItemWithValidation(1, "F", 5);
        ArrayList<ListItemData> item = dt.getListItems(1);

        Assert.assertEquals(item.size(), 1);
        Assert.assertTrue(item.get(0).getName().equals("F"));
        Assert.assertEquals(item.get(0).getQuantity(), 5);
    }

    public void testDeleteList() throws Exception
    {
        dt.deleteListWithValidation(1);
        Assert.assertEquals(dt.getLists().size(),0);
    }

    public void testDeleteItem() throws Exception
    {
        dt.deleteItemWithValidation(1);
        Assert.assertEquals(dt.getListItems(1).size(),0);
    }
}