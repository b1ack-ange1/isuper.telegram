/**
 * Copyright 2014-2016 Super Wayne
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package isuper.telegram;

import java.io.IOException;

import org.isuper.telegram.TelegramClient;
import org.isuper.telegram.models.User;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Super Wayne
 *
 */
public class TelegramClientTest {
	
	/**
	 * 
	 */
	private static final String TELEGRAM_TEST_BOT_TOKEN = "253269670:AAG5XOj8ucL0KZxNteYFb7Int7m1QbDzmg8";
	private static TelegramClient CLIENT;
	
	@BeforeClass
	public static void init() {
		CLIENT = new TelegramClient();
	}
	
	@Test
	public void testGetMe() {
		User bot = CLIENT.getMe(TELEGRAM_TEST_BOT_TOKEN);
		Assert.assertNotNull(bot);
		Assert.assertEquals("randomtestrobot", bot.username);
	}

	@AfterClass
	public static void setup() throws IOException {
		if (CLIENT != null) {
			CLIENT.close();
		}
	} 

}
