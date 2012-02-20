package me.sirtyler.JunkyardCreek;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Util {
	public String replace(String aText) {
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("");
			} else if (character == '>') {
				result.append("");
			} else if (character == '&') {
				result.append("");
			} else if (character == '\"') {
				result.append("");
			} else if (character == '\t') {
				result.append("");
			} else if (character == '!') {
				result.append("");
			} else if (character == '#') {
				result.append("");
			} else if (character == '$') {
				result.append("");
			} else if (character == '%') {
				result.append("");
			} else if (character == '\'') {
				result.append("");
			} else if (character == '(') {
				result.append("");
			} else if (character == ')') {
				result.append("");
			} else if (character == '*') {
				result.append("");
			} else if (character == '/') {
				result.append("");
			} else if (character == ':') {
				result.append("");
			} else if (character == ';') {
				result.append("");
			} else if (character == '=') {
				result.append("");
			} else if (character == '?') {
				result.append("");
			} else if (character == '@') {
				result.append("");
			} else if (character == '[') {
				result.append("");
			} else if (character == '\\') {
				result.append("");
			} else if (character == ']') {
				result.append("");
			} else if (character == '^') {
				result.append("");
			} else if (character == '_') {
				result.append("");
			} else if (character == '`') {
				result.append("");
			} else if (character == '{') {
				result.append("");
			} else if (character == '|') {
				result.append("");
			} else if (character == '}') {
				result.append("");
			} else if (character == '~') {
				result.append("");
			} else if (character == ' ') {
				result.append("");
			} else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
}
