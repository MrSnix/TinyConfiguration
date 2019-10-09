package org.tinyconfiguration.base;

/**
 * Sets the configuration policy
 * <p></p>
 * <p>The {@link ConfigurationPolicy} is a <b>really important</b> rule,<br>
 * which must be applied carefully and with some consideration
 * by the developer.</p>
 * <p>
 * This value affects the behaviours of the following methods:
 * <ul>
 *      <li>{@link ConfigurationIO#read(Configuration)}</li>
 *      <li>{@link ConfigurationIO#write(Configuration)}</li>
 * </ul>
 *
 * <br>
 * <p>
 * On {@link ConfigurationPolicy#STRICT_MODE}:
 * <ol>
 *     <li>Unknown properties read from the configuration file will cause an exception</li>
 *     <li>Any missing property from the configuration file will cause an exception</li>
 *     <li>Any invalid syntax from the configuration file will cause an exception</li>
 * </ol>
 * It is recommended when:
 * <ul>
 *     <li>You <b>do not want</b> allow the user to externally modify the configuration file</li>
 *     <li>You <b>do not want</b> missing property from the configuration file</li>
 * </ul>
 * <p>
 * On {@link ConfigurationPolicy#TOLERANT_MODE}:
 * <ol>
 *     <li>Unknown properties read from the configuration file will be loaded</li>
 *     <li>Any missing property from the configuration file will be ignored</li>
 *     <li>Any invalid syntax from the configuration file will cause an exception</li>
 * </ol>
 * It is recommended when:
 * <ul>
 *     <li>You <b>want</b> allow the user to externally modify the configuration file</li>
 *     <li>You <b>want</b> to ignore missing property from the configuration file</li>
 * </ul>
 * <p>
 * By default, the configuration policy is set to {@link ConfigurationPolicy#STRICT_MODE}
 *
 * @author G. Baittiner
 * @version 0.1
 */
public enum ConfigurationPolicy {
    STRICT_MODE, TOLERANT_MODE
}
