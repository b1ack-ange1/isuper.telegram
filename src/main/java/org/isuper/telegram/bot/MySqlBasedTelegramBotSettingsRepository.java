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
package org.isuper.telegram.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.isuper.common.utils.Preconditions;

/**
 * @author Super Wayne
 *
 */
public abstract class MySqlBasedTelegramBotSettingsRepository implements TelegramBotSettingsRepository {

	/**
	 * @param chatOrUserID
	 * 						The chat ID or user ID
	 * @param robotID
	 * 						The robot ID
	 * @param propKey
	 * 						The property key
	 * @param propValue
	 * 						The property value
	 */
	@Override
	public void saveRobotSettings(String chatOrUserID, String robotID, String propKey, String propValue) {
		if (Preconditions.isEmptyString(chatOrUserID) || Preconditions.isEmptyString(robotID) || Preconditions.isEmptyString(propKey) || Preconditions.isEmptyString(propValue)) {
			return;
		}
		String update = "INSERT INTO " + getTableName() + "(chat_or_user_id, robot_id, prop_key, prop_value) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE prop_value = VALUES(prop_value)";
		try (
				Connection conn = this.getDataSource().getConnection();
				PreparedStatement updatePs = conn.prepareStatement(update);
				) {
			updatePs.setString(1, chatOrUserID);
			updatePs.setString(2, robotID);
			updatePs.setString(3, propKey);
			updatePs.setString(4, propValue);
			updatePs.executeUpdate();
		} catch (SQLException e) {
			getLogger().error(e.getMessage(), e);
		}
	}

	/**
	 * @param chatOrUserID
	 * 						The chat ID or user ID
	 * @param robotID
	 * 						The robot ID
	 * @return
	 * 						A hash map of all properties
	 */
	@Override
	public Map<String, String> loadRobotSettings(String chatOrUserID, String robotID) {
		Map<String, String> props = new HashMap<>();
		String query = "SELECT prop_key, prop_value FROM " + getTableName() + " WHERE chat_or_user_id = ? AND robot_id = ? AND prop_key = ?";
		try (
				Connection conn = this.getDataSource().getConnection();
				PreparedStatement queryPs = conn.prepareStatement(query);
				) {
			queryPs.setString(1, chatOrUserID);
			queryPs.setString(2, robotID);
			try (
					ResultSet rs = queryPs.executeQuery();
					) {
				while (rs.next()) {
					props.put(rs.getString("prop_key"), rs.getString("prop_value"));
				}
			}
		} catch (SQLException e) {
			getLogger().error(e.getMessage(), e);
		}
		return props;
	}

	/**
	 * @param chatOrUserID
	 * 						The chat ID or user ID
	 * @param robotID
	 * 						The robot ID
	 * @param propKey
	 * 						The property key
	 * @return
	 * 						The property value
	 */
	@Override
	public String getRobotSettings(String chatOrUserID, String robotID, String propKey) {
		String query = "SELECT prop_value FROM " + getTableName() + " WHERE chat_or_user_id = ? AND robot_id = ? AND prop_key = ?";
		try (
				Connection conn = this.getDataSource().getConnection();
				PreparedStatement queryPs = conn.prepareStatement(query);
				) {
			queryPs.setString(1, chatOrUserID);
			queryPs.setString(2, robotID);
			queryPs.setString(3, propKey);
			try (
					ResultSet rs = queryPs.executeQuery();
					) {
				if (rs.next()) {
					return rs.getString("prop_value");
				}
			}
		} catch (SQLException e) {
			getLogger().error(e.getMessage(), e);
		}
		return null;
	}
	
	protected abstract DataSource getDataSource();
	
	protected abstract String getTableName();
	
	protected abstract Logger getLogger();

}
