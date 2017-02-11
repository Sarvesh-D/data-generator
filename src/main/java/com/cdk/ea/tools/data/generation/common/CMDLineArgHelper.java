package com.cdk.ea.tools.data.generation.common;

/**
 * Interface to be implemented by any enum that serves as a container for
 * arguments that can be passed as CLI args. This interface provides a
 * consistent way to capture help for the CLI arg.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
@FunctionalInterface
public interface CMDLineArgHelper {

    /**
     * Returns the help/usage message.
     * 
     * @return help/usage message.
     */
    String getHelp();

}
