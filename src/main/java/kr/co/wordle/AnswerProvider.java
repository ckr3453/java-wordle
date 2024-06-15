package kr.co.wordle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;

public class AnswerProvider {

    private static final String WORDS_FILE_PATH = "words.txt";
    private static final LocalDate REFERENCE_DATE = LocalDate.of(2021, 6, 19);

    private AnswerProvider() {
    }

    private static List<String> getWords() {
        try {
            URL resource = AnswerProvider.class.getClassLoader().getResource(WORDS_FILE_PATH);
            if (resource != null) {
                return Files.readAllLines(Paths.get(resource.toURI()));
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static String todayAnswer() {
        List<String> words = getWords();
        int dayDiff = getTodayIndex();
        return words.get(dayDiff % words.size());
    }

    private static int getTodayIndex() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(REFERENCE_DATE, currentDate);
        return period.getDays();
    }

    public static boolean isAnswerInWords(String input) {
        try {
            URL resource = AnswerProvider.class.getClassLoader().getResource(WORDS_FILE_PATH);
            List<String> strings = Files.readAllLines(Paths.get(resource.toURI()));
            return strings.contains(input);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
