package by.incubator.application.controllers;

import by.incubator.application.game.Board;
import by.incubator.application.game.Game;
import by.incubator.application.players.AbstractPlayer;
import by.incubator.application.players.impl.ComputerPlayer;
import by.incubator.application.players.impl.HumanPlayer;
import by.incubator.application.players.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class MyController {
    @GetMapping("/createGame")
    public void gameCreating(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String role = request.getParameter("role");
        Game game;
        Board board = new Board();
        AbstractPlayer player1;
        AbstractPlayer player2;

        if(role.equals("cross")) {
            player1 = new HumanPlayer(board, Role.CROSS);
            player2 = new ComputerPlayer(board, Role.ZERO);
        } else {
            player1 = new HumanPlayer(board, Role.ZERO);
            player2 = new ComputerPlayer(board, Role.CROSS);
        }

        game = new Game(player1, player2);

        session.setAttribute("game", game);
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
        } else {
            response.sendRedirect("http://localhost:8081/board");
        }

    }

    @GetMapping("/congratulation")
    public String congratulation(Model model, HttpSession session) {
        model.addAttribute("winner", session.getAttribute("winner"));
        return "congratulation";
    }

    @GetMapping("/roleChoice")
    public String roleChoice() {
        return "roleChoice";
    }
}
