/**
 * 
 */
package org.freesource.mobedu.dao;

import java.util.List;

import org.freesource.mobedu.dao.model.User;
import org.freesource.mobedu.utils.MobileEduException;

/**
 * User bean manager to handle User DAO requests
 * 
 */
public interface UserManagerService {

	String getMessage();

	/**
	 * Handle all the start service request. Below table illustrates the
	 * possible options of a start request by the user:
	 * <table border="1">
	 * <tr>
	 * <th>User Exists</th>
	 * <th>User IS ACTIVE</th>
	 * <th>Action to be taken</th>
	 * </tr>
	 * <tr>
	 * <td align="center">No</td>
	 * <td align="center">---</td>
	 * <td>Success: Register</td>
	 * </tr>
	 * </tr>
	 * <tr>
	 * <td align="center">Yes</td>
	 * <td align="center">No</td>
	 * <td>Success: Register</td>
	 * </tr>
	 * <tr>
	 * <td align="center">Yes</td>
	 * <td align="center">Yes</td>
	 * <td>Error: Already Registered</td>
	 * </tr>
	 * </table>
	 * 
	 * @param user
	 * @return
	 * @throws MobileEduException
	 */
	String startService(User user) throws MobileEduException;

	/**
	 * Handle all the stop service request. Below table illustrates the possible
	 * options of a stop request by the user:
	 * <table border="1">
	 * <tr>
	 * <th>User Exists</th>
	 * <th>User IS ACTIVE</th>
	 * <th>Action to be taken</th>
	 * </tr>
	 * <tr>
	 * <td align="center">No</td>
	 * <td align="center">---</td>
	 * <td>Error: Not registered</td>
	 * </tr>
	 * </tr>
	 * <tr>
	 * <td align="center">Yes</td>
	 * <td align="center">No</td>
	 * <td>Error: Not registered</td>
	 * </tr>
	 * <tr>
	 * <td align="center">Yes</td>
	 * <td align="center">Yes</td>
	 * <td>Success: Unregister</td>
	 * </tr>
	 * </table>
	 * 
	 * @param regUser
	 *            - The user to be unregistered
	 * @return String - The reply message to be sent
	 * @throws MobileEduException
	 */
	String stopService(User regUser) throws MobileEduException;

	/**
	 * Validate the arguments passed and then populate the passed User object
	 * with the appropriate values
	 * 
	 * @param user
	 * @param mobileHash
	 * @param arguments
	 * @return <i>true</i>: if the arguments are valid <br/>
	 *         <i>false</i>: if the arguments are invalid or any other error
	 *         occurred
	 */
	boolean validatePopulateUser(User user, String mobileHash, String arguments);

	/**
	 * Method to get the count of the total number of users in the DB. This
	 * includes even the inactive users, simulator entries etc.
	 * 
	 * @return total count of users
	 */
	long getNumberOfUser();

	/**
	 * Method to get all users from the DB. This includes even the inactive
	 * users, simulator entries etc. Use it with caution as it may fecth large
	 * amount of data
	 * 
	 * @return total count of users
	 */
	List<User> getAllRegisteredUsers();
	

	/**
	 * Method to get the the user based on the given User-ID
	 * 
	 * @return total count of users
	 */
	User getUserById(int userId);

}
