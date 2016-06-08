/**
 * 
 */
package isuper.telegram.modes;

import org.isuper.telegram.models.Update;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Super Wang
 *
 */
public class UpdateTest {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Test
	public void testParse() {
		try {
			Update update = MAPPER.readValue("{\"update_id\": 40737835,"
					+ " \"inline_query\": {"
					+ "\"id\": \"480167263633693677\","
					+ " \"from\": {\"id\": 111797653, \"first_name\": \"Super\", \"last_name\": \"@AS16-NOV-08\", \"username\": \"StarVoyager\"},"
					+ " \"location\": {\"latitude\": 39.94991,\"longitude\": 116.307506},"
					+ " \"query\": \"\","
					+ " \"offset\": \"\"}}", Update.class);
			System.out.println(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
