/**
 * 
 */
package org.isuper.telegram;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.models.Command;
import org.isuper.telegram.models.Message;
import org.isuper.telegram.models.Update;
import org.isuper.telegram.models.inline.ChosenInlineResult;
import org.isuper.telegram.models.inline.InlineQuery;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Super Wang
 *
 */
public abstract class TelegramRobot {

	protected void handleUpdate(Update update) {
		if (update == null) {
			return;
		}
		ResourceBundle bundle = this.getResourceBundle(update);
		if (update.inlineQuery != null) {
			this.handleInlineQuery(bundle, update.inlineQuery);
		} else if (update.chosenInlineResult != null) {
			this.handleChosenInlineResult(bundle, update.chosenInlineResult);
		} else if (update.message != null) {
			if (update.message.newChatParticipant != null) {
				this.handleNewParticipant(bundle, update.message);
			} else if (update.message.location != null) {
				this.handleLocationMessage(bundle, update.message);
			} else if (!Preconditions.isEmptyString(update.message.text)) {
				Command cmd = TelegramUtils.parseCommandFromText(update.message.text);
				if (cmd == null) {
					this.handleOrdinaryTextMessage(bundle, update.message);
				} else if (Preconditions.isEmptyString(cmd.toBot) || getRobotUsername().equalsIgnoreCase(cmd.toBot)) {
					this.handleCommands(bundle, cmd, update.message);
				}
			}
		} else {
			try {
				getLogger().warn(TelegramUtils.getObjectMapper().writeValueAsString(update));
			} catch (JsonProcessingException e) {
				getLogger().error(e.getMessage(), e);
			}
		}
	}

	protected Logger getLogger() {
		return LogManager.getLogger(String.format("%s.bots.%s", TelegramRobot.class.getPackage().getName(), getRobotUsername()));
	}
	
	protected abstract ResourceBundle getResourceBundle(Update update);
	protected abstract String getRobotUsername();

	protected abstract void handleNewParticipant(ResourceBundle bundle, Message message);
	protected abstract void handleLocationMessage(ResourceBundle bundle, Message message);
	protected abstract void handleOrdinaryTextMessage(ResourceBundle bundle, Message message);
	protected abstract void handleCommands(ResourceBundle bundle, Command cmd, Message message);
	
	protected abstract void handleInlineQuery(ResourceBundle bundle, InlineQuery inlineQuery);
	protected abstract void handleChosenInlineResult(ResourceBundle bundle, ChosenInlineResult chosenInlineResult);
	
}
