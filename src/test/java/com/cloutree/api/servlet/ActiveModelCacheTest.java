

package com.cloutree.api.servlet;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;


public class ActiveModelCacheTest {

	@Test
	public void shouldGetModelFile() {
		
		File file = null;
		
		try {
			file = ActiveModelCache.requestModelFile("MARC-TEST");
		} catch (Exception e) {
			Assert.assertTrue("Exception thrown: " + e.getMessage(), false);
		}
		
		Assert.assertNotNull(file);
		
	}
	
	
}

