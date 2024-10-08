/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Projeto_Integrador.app.controller;

import Projeto_Integrador.app.dao.IngredienteDAO;
import Projeto_Integrador.app.model.DTO.IngredienteDTO;
import Projeto_Integrador.app.model.Ingrediente;
import Projeto_Integrador.app.utils.ResultadoValidacao;
import Projeto_Integrador.app.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author henri
 */
public class IngredienteController {

    private IngredienteDAO ingredienteDAO;

    public IngredienteController() {
        this.ingredienteDAO = new IngredienteDAO();
    }

    public ResultadoValidacao validarIngrediente(IngredienteDTO ingrediente) {
        ResultadoValidacao resultado = new ResultadoValidacao(); // Cria um objeto para armazenar o resultado da validação

        // Verifica se o nome está vazio
        if (ingrediente.getNome().isEmpty()) {
            resultado.setValido(false);
            resultado.setMensagem("Nome do ingrediente não pode estar vazio.");
            return resultado;
        }
        if (ingrediente.getValorStr().contains(",")) {
            ingrediente.setValorStr(ingrediente.getValorStr().replace(",", "."));
        }
        // Verifica se o valor é um número válido
        ingrediente.setValorStr(Utils.formatarStringParaFloat(ingrediente.getValorStr()));
        try {
            float valorFloat = Float.parseFloat(ingrediente.getValorStr());
            if (valorFloat <= 0) {
                resultado.setValido(false);
                resultado.setMensagem("Valor do preço deve ser um número positivo.");
                return resultado;
            }
        } catch (NumberFormatException e) {
            resultado.setValido(false);
            resultado.setMensagem("Valor do preço  deve ser um número válido.");
            return resultado;
        }
        // Verifica se a quantidade é um número válido
        ingrediente.setQuantidadeStr(Utils.formatarStringParaFloat(ingrediente.getQuantidadeStr()));
        try {
            float quantidadeFloat = Float.parseFloat(ingrediente.getQuantidadeStr());
            if (quantidadeFloat <= 0) {
                resultado.setValido(false);
                resultado.setMensagem("Valor da  quantidade deve ser um número positivo.");
                return resultado;
            }
        } catch (NumberFormatException e) {
            resultado.setValido(false);
            resultado.setMensagem("Valor da quantidade deve ser um número válido.");
            return resultado;
        }

        // Verifica se o tipo foi selecionado
        if ("Selecione um tipo".equals(ingrediente.getTipo())) {
            resultado.setValido(false);
            resultado.setMensagem("Selecione um tipo de ingrediente.");
            return resultado;
        }

        // Se passar por todas as verificações, define o resultado como válido e retorna
        resultado.setValido(true);
        return resultado;
    }
      public static ResultadoValidacao validarIngredientesQuantidades(Map<String, String> ingredientesEQuantidades) {
        ResultadoValidacao resultado = new ResultadoValidacao();
        for (Map.Entry<String, String> entry : ingredientesEQuantidades.entrySet()) {
            String Ingrediente = entry.getKey();
            String val = entry.getValue();
            if (Ingrediente.equals("Selecione o ingrediente")) {
                resultado.setValido(false);
                resultado.setMensagem("Selecione o ingrediente corretamente.");
                return resultado;
            }
            if (val.contains(",")) {
                val = val.replace(",", ".");
            }
            try {
                float quantidadeFloat = Float.valueOf(val);
                if (quantidadeFloat <= 0) {
                resultado.setValido(false);
                resultado.setMensagem("Valor da quantidade deve ser um número positivo.");
                return resultado;
            }
            } catch (NumberFormatException e) {
                resultado.setValido(false);
                resultado.setMensagem("Valor da quantidade dos ingredientes deve ser um número válido.");
                return resultado;
            }
        }
        resultado.setValido(true);
        return resultado;
    }
    public void carregarIngredientesParaTabela(JTable table) {
        try {
            //  selecionando todos items da table na db
            List<Ingrediente> ingredientes = new IngredienteDAO().selectAll();
            if (ingredientes != null) {
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                tableModel.setRowCount(0);

                for (Ingrediente ingrediente : ingredientes) {
                    String valor = String.format("%.02f", ingrediente.getValor());
                    tableModel.addRow(new Object[]{ingrediente.getId(), ingrediente.getNome(), valor, ingrediente.getQuantidade(), ingrediente.getTipo()});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public boolean cadastrar(IngredienteDTO ingredienteDTO) {
        try {
            ResultadoValidacao resultado = validarIngrediente(ingredienteDTO);
            if (resultado.isValido()) {
                float valor = Float.parseFloat(ingredienteDTO.getValorStr());
                float quantidade = Float.parseFloat(ingredienteDTO.getQuantidadeStr());
                Ingrediente ingrediente = new Ingrediente(ingredienteDTO.getNome(), valor, quantidade, ingredienteDTO.getTipo());

                ingredienteDAO.cadastrarIngrediente(ingrediente);
                JOptionPane.showMessageDialog(null, "Ingrediente cadastrado com sucesso!!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao processar dados: " + resultado.getMensagem());
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean alterarIngrediente(IngredienteDTO ingredienteDTO) {
        try {
            ResultadoValidacao resultado = validarIngrediente(ingredienteDTO);
            if (resultado.isValido()) {
                float valor = Float.parseFloat(ingredienteDTO.getValorStr());
                float quantidade = Float.parseFloat(ingredienteDTO.getQuantidadeStr());
                Ingrediente ingrediente = ingredienteDAO.selectById(ingredienteDTO.getId());
                ingrediente.setNome(ingredienteDTO.getNome());
                ingrediente.setValor(valor);
                ingrediente.setQuantidade(quantidade);
                ingrediente.setTipo(ingredienteDTO.getTipo());
                if (ingredienteDAO.alterarIngrediente(ingrediente)) {
                    JOptionPane.showMessageDialog(null, "Alteração feita com sucesso");
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO na comunicaçao com o DB");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao processar ingrediente: " + resultado.getMensagem());
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean removerIngredientes(ArrayList<Integer> ids) {
        boolean sucesso = true;
        try {
            for (Integer id : ids) {
                ingredienteDAO.removerIngrediente(id);
            }
        } catch (SQLException e) {
            sucesso = false;
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return sucesso;
    }
    public List<IngredienteDTO> carregarIngredienteDTOs() {
        try {
            List<Ingrediente> ingredientes = ingredienteDAO.selectAll();
            List<IngredienteDTO> ingredientesDTO = new ArrayList<>();
            for (Ingrediente ingrediente : ingredientes) {
                IngredienteDTO ingredienteDTO = new IngredienteDTO(
                        ingrediente.getId(),
                        ingrediente.getNome(),
                        ingrediente.getValor(),
                        ingrediente.getQuantidade(),
                        ingrediente.getTipo());
                ingredientesDTO.add(ingredienteDTO);
            }
            return ingredientesDTO;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }
}
