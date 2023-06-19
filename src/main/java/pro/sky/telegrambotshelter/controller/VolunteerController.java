package pro.sky.telegrambotshelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambotshelter.entity.Report;
import pro.sky.telegrambotshelter.service.VolunteerService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class VolunteerController {

    private final VolunteerService service;

    VolunteerController(VolunteerService service){
        this.service = service;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return String.format("%s %s", HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * the method returns the latest user reports
     */
    @GetMapping//
    public Map<Long, Report> getAllLastReports() {
            return service.getAllLastReports();
    }
    //возвращает очеты определенного пользователя
    /**
     * the method returns the points of a certain user
     * @param chatId - chat identifier
     */
    @GetMapping("/{chatId}")
    public Collection<Report> getAllByChatId(@PathVariable("chatId") Long chatId) {
        return service.getAllByChatId(chatId);
    }

    //отправляет пользователю принят ли отчет
    /**
     * the method sends the user whether the report is accepted
     * @param chatId - chat identifier
     * @param isGood - is the report suitable
     */
    @PostMapping ("/{chatId}/{isGood}")
    public String isGood(@PathVariable("chatId") Long chatId, @PathVariable("isGood") boolean isGood) {
        return service.sendMessageAboutReport(chatId, isGood);
    }

   //отправляет чаты с определенным количеством отчетов(дней) и больше
    /**
     * the method sends chats with a certain number of reports (days) and more
     * @param days - days of reports
     */
    @PostMapping ("/send-a-days/{days}")
    public List<Long> getAllWhereCount31(@PathVariable("days") int days) {
        return service.getAllWhereCount(days);
    }

    //отправляем сообщение о пользователю сообщение о продлении испытательного срока на 14 дней
    /**
     * the method sends a message to the user about the extension of the trial period for 14 days
     * @param chatId - chat identifier
     */
    @PostMapping ("/{chatId}/14")
    public String DayExtension14(@PathVariable("chatId") Long chatId) {
        return service.sendMessageAboutExtension(chatId,14);
    }

    //отправляем сообщение о пользователю сообщение о продлении испытательного срока на 30 дней
    /**
     * the method sends a message to the user about the extension of the trial period for 30 days
     * @param chatId - chat identifier
     */
    @PostMapping ("/{chatId}/30")
    public String DayExtension30(@PathVariable("chatId") Long chatId) {
        return service.sendMessageAboutExtension(chatId, 30);
    }

    //если пользователь прошел испытательный срок, его можно удалить
    /**
     * the method sends a message to the user that he has passed the probation period and removes him from the databases
     * @param chatId - chat identifier
     */
    @DeleteMapping ("/probation-period/{chatId}/{isPassed}")
    public String isPassedTheProbationPeriod(@PathVariable("chatId") Long chatId, @PathVariable("isPassed") boolean isPassed) {
        return service.isPassedTheProbationPeriod(chatId, isPassed);
    }



}
