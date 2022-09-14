package by.incubator.application.controllers;

import by.incubator.application.game.Board;
import by.incubator.application.game.Game;
import by.incubator.application.players.AbstractPlayer;
import by.incubator.application.players.Role;
import by.incubator.application.players.impl.ComputerPlayer;
import by.incubator.application.players.impl.HumanPlayer;
import by.incubator.application.players.impl.hardLevel.HardComputerPlayer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class MyController {
    private static final Logger log = Logger.getLogger(MyController.class);

    @GetMapping("/createGame")
    public void gameCreating(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String role = request.getParameter("role");
        Game game;
        Board board = new Board();
        AbstractPlayer player1 = new HumanPlayer();
        AbstractPlayer player2;

        String difficulty = (String) session.getAttribute("difficulty");

        if (difficulty.equals("player")) {
            player2 = new HumanPlayer();
        } else if (difficulty.equals("hard")) {
            player2 = new HardComputerPlayer();
        } else {
            player2 = new ComputerPlayer();
        }

        player1.setBoard(board);
        player2.setBoard(board);

        if (role.equals("CROSS")) {
            player1.setRole(Role.CROSS);
            player2.setRole(Role.ZERO);
        } else {
            player1.setRole(Role.ZERO);
            player2.setRole(Role.CROSS);
        }

        if (player2 instanceof HardComputerPlayer) {
            ((HardComputerPlayer) player2).init();
        }

        game = new Game(player1, player2);

        session.setAttribute("game", game);
        session.setAttribute("role", role);
        response.sendRedirect("http://localhost:8081/board");
    }

    @GetMapping("/board")
    public String board(Model model, HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        int[] lines = {0, 1, 2};
        int[] columns = {0, 1, 2};

        if (game == null) {
            Board board = new Board();
            AbstractPlayer player1 = new HumanPlayer(board, Role.CROSS);
            AbstractPlayer player2 = new HumanPlayer(board, Role.ZERO);
            game = new Game(player1, player2);
        }

        model.addAttribute("lines", lines);
        model.addAttribute("columns", columns);
        model.addAttribute("boardDto", game.getBoard().getDto());
        session.setAttribute("game", game);
        return "board";
    }

    @GetMapping("/move")
    public void move(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Game game = (Game) session.getAttribute("game");
        int field = Integer.parseInt(request.getParameter("field"));
        game.nextTurn(field);



        if (game.getWinner() != null) {
            session.setAttribute("winner", game.getWinner());
            response.sendRedirect("http://localhost:8081/congratulation");
        } else if (game.isFinished()) {
            response.sendRedirect("http://localhost:8081/draw");
        } else {
            response.sendRedirect("http://localhost:8081/board");
        }
    }

    @GetMapping("/congratulation")
    public String congratulation(Model model, HttpSession session) {
        model.addAttribute("winner", session.getAttribute("winner"));
        logResult(session);
        return "congratulation";
    }

    @GetMapping("/roleChoice")
    public String roleChoice(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String difficulty = request.getParameter("difficulty");
        session.setAttribute("difficulty", difficulty);
        if (difficulty.equals("player")) {
            response.sendRedirect("http://localhost:8081/createGame?role=cross");
        }
        return "roleChoice";
    }

    @GetMapping("/difficultyChoice")
    public String difficultyChoice() {
        return "difficultyChoice";
    }

    @GetMapping("/restart")
    public String restart(HttpSession session) {
        session.invalidate();
        return "difficultyChoice";
    }

    @GetMapping("/draw")
    public String draw(HttpSession session) {
        logResult(session);
        return "draw";
    }

    private void logResult(HttpSession session) {
        String message = "Difficulty: " + session.getAttribute("difficulty") + " Role: " +
                session.getAttribute("role") + " Result: ";

        if (session.getAttribute("winner") != null) {
            if (session.getAttribute("winner").toString().equals(session.getAttribute("role"))) {
                message += "win";
            } else {
                message += "lose";
            }
        } else {
            message += "draw";
        }

        log.info(message);
    }
}
