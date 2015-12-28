import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hash
{
	private String hashToString(String serializedModel, byte[] key)
	{
		String result = null;
		Mac sha512_HMAC;

		try
		{
			sha512_HMAC = Mac.getInstance("HmacSHA512");
			SecretKeySpec secretkey = new SecretKeySpec(key, "HmacSHA512");
			sha512_HMAC.init(secretkey);
			byte[] mac_data = sha512_HMAC.doFinal(serializedModel.getBytes("UTF-8"));
			result = Base64.getEncoder().encodeToString(mac_data);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
