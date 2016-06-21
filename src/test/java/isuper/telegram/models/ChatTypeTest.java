/**
 * 
 */
package isuper.telegram.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.isuper.telegram.models.ChatType;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Super Wang
 *
 */
public class ChatTypeTest {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Test
	public void testParse() {
		try {
			List<ChatType> types = MAPPER.readValue("[ \"private\", \"group\", \"supergroup\", \"channel\" ]", new TypeReference<ArrayList<ChatType>>() {});
			Assert.assertEquals(4, types.size());
			Assert.assertEquals(ChatType.PRIVATE, types.get(0));
			Assert.assertEquals(ChatType.GROUP, types.get(1));
			Assert.assertEquals(ChatType.SUPERGROUP, types.get(2));
			Assert.assertEquals(ChatType.CHANNEL, types.get(3));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			MAPPER.readValue("null", ChatType.class);
		} catch (IOException e) {
			Assert.assertTrue(e instanceof JsonMappingException);
		}

		String badString = "asdf";
		try {
			MAPPER.readValue(String.format("\"%s\"", badString), ChatType.class);
		} catch (IOException e) {
			Assert.assertTrue(e instanceof JsonMappingException);
		}

		try {
			MAPPER.readValue("\"\"", ChatType.class);
		} catch (IOException e) {
			Assert.assertTrue(e instanceof JsonMappingException);
		}

	}

}
