package com.sertice.plugins.jenkins.utils;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public final class Pacal {

	private static final Function<String, String> CAPITALIZER = new Function<String, String>() {
		@Override
		public String apply(String s) {
			return StringUtils.capitalize(s.toLowerCase());
		}
	};

	public static String convert(String value) {
		Iterable<String> split = Splitter.on('_').split(value);
		Iterable<String> trans = Iterables.transform(split, CAPITALIZER);
		return Joiner.on("").join(trans);
	}

}
