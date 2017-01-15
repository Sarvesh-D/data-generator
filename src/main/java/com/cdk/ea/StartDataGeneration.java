package com.cdk.ea;

import com.cdk.ea.data.query.QueryRunner;

public class StartDataGeneration {
    
    private StartDataGeneration() {
	// suppressing default constructor
    }

    public static void main(String... args) {
	QueryRunner.from(args).run();
    }

}
