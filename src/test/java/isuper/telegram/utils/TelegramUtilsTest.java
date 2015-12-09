/**
 * 
 */
package isuper.telegram.utils;

import org.isuper.telegram.models.Command;
import org.isuper.telegram.utils.TelegramUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Super Wang
 *
 */
public class TelegramUtilsTest {
	
	@Test
	public void testParseCommnad() {
		Command cmd = TelegramUtils.parseCommandFromText("/hello");
		Assert.assertNotNull(cmd);
		Assert.assertEquals("hello", cmd.type);
		Assert.assertNull(cmd.toBot);
		Assert.assertNull(cmd.argument);

		cmd = TelegramUtils.parseCommandFromText("/hello ");
		Assert.assertNotNull(cmd);
		Assert.assertEquals("hello", cmd.type);
		Assert.assertNull(cmd.toBot);
		Assert.assertEquals("", cmd.argument);

		cmd = TelegramUtils.parseCommandFromText("/hello Jarvis");
		Assert.assertNotNull(cmd);
		Assert.assertEquals("hello", cmd.type);
		Assert.assertNull(cmd.toBot);
		Assert.assertEquals(cmd.argument, "Jarvis");

		cmd = TelegramUtils.parseCommandFromText("/hello@enl_javis_bot");
		Assert.assertNotNull(cmd);
		Assert.assertEquals("hello", cmd.type);
		Assert.assertEquals(cmd.toBot, "enl_javis_bot");
		Assert.assertNull(cmd.argument);

		cmd = TelegramUtils.parseCommandFromText("/hello@enl_javis_bot ");
		Assert.assertNotNull(cmd);
		Assert.assertEquals("hello", cmd.type);
		Assert.assertEquals(cmd.toBot, "enl_javis_bot");
		Assert.assertEquals("", cmd.argument);

		cmd = TelegramUtils.parseCommandFromText("/hello@enl_javis_bot Jarvis");
		Assert.assertNotNull(cmd);
		Assert.assertEquals("hello", cmd.type);
		Assert.assertEquals(cmd.toBot, "enl_javis_bot");
		Assert.assertEquals(cmd.argument, "Jarvis");

	}

}
