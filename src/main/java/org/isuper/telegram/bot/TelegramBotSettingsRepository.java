/**
 *  Copyright (C) 2014-2016  Super Wayne
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.isuper.telegram.bot;

import java.util.Map;

/**
 * @author Super Wayne
 *
 */
public interface TelegramBotSettingsRepository {

	String getRobotSettings(String chatOrUserID, String robotID, String propKey);

	Map<String, String> loadRobotSettings(String chatOrUserID, String robotID);

	void saveRobotSettings(String chatOrUserID, String robotID, String propKey, String propValue);

}
