package xyz.subho.clone.twitter.utility;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Utility {
	
	/**
	 * @param String uuid
	 * @return TRUE or FALSE if the given String is a valid UUID
	 */
	public boolean isUUID(String uuid)	{
		
		try	{
			UUID.fromString(uuid);
			return true;
		}
		catch (Exception exp)	{
			return false;
		}
	}
	
	/**
	 * @param String uuid
	 * @return UUID uuid or NULL if invalid
	 */
	public UUID converStringToUUID(String uuid)	{
		return isUUID(uuid) ? UUID.fromString(uuid) : null;
	}

}
