import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

public class Funcionario implements Serializable {
    private int id;
    private String nome;
    private String sobrenome;
    private int idade;
    private String cargo;
    private double salario;
    private String filial;

    //construtor
    public Funcionario(int id, String nome, String sobrenome, int idade, String cargo, double salario, String filial){
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.cargo = cargo;
        this.filial = filial;
        this.salario = salario;
    }

    //getters e setters
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getSobrenome(){
        return sobrenome;
    }
    public void setSobrenome(String sobrenome){
        this.sobrenome = sobrenome;
    }
    public int getIdade(){
        return idade;
    }
    public void setIdade(int idade){
        this.idade = idade;
    }
    public String getCargo(){
        return cargo;
    }
    public void setCargo(String cargo){
        this.cargo = cargo;
    }
    public String getFilial(){
        return filial;
    }
    public void setFilial(String filial){
        this.filial = filial;
    }
    public double getSalario(){
        return salario;
    }
    public void setSalario(double salario){
        this.salario = salario;
    }

    //exibir informações do funcionário
    public String toString (){
        return "ID: " + id + ", Nome: " + nome + ", Sobrenome: " + sobrenome + ", Idade: " + idade + ", Cargo: " + cargo + ", Salário: " + salario + ", Filial: " + filial;
    }
    
    public static void main(String[] args) {
        // Inicializa a aplicação JavaFX
        Application.launch(GerenciadorFuncionariosApp.class, args);
    }

    public static class GerenciadorFuncionarios implements Serializable {
        private List<Funcionario> funcionarios;

        public GerenciadorFuncionarios(){
            funcionarios = new ArrayList<>();
        }

        public void adicionarFuncionario(Funcionario funcionario){
            funcionarios.add(funcionario);
        }

        public void removerFuncionario(int id){
            for (Funcionario funcionario : funcionarios){
                if (funcionario.getId() == id){
                    funcionarios.remove(funcionario);
                    break;
                }
            }
        }

        public void listarFuncionarios(){
            for (Funcionario funcionario : funcionarios){
                System.out.println(funcionario);
            }
        }

        public List<Funcionario> getFuncionarios() {
            return funcionarios;
        }

        public Funcionario getFuncionarioPorId(int id) {
            for (Funcionario funcionario : funcionarios) {
                if (funcionario.getId() == id) {
                    return funcionario;
                }
            }
            return null; // Se nenhum funcionário com o ID especificado for encontrado
        }

            @SuppressWarnings("unchecked")
            public void carregarFuncionarios(String nomedoarquivo){
            try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomedoarquivo))){
                funcionarios = (List<Funcionario>) inputStream.readObject();
                System.out.println("Dados dos funcionários carregados com sucesso!");
            } catch (IOException | ClassNotFoundException e){
                System.err.println("Erro ao carregar dados dos funcionários: "+ e.getMessage());
            }
            }
    }
    //início do bloco de JavaFX
    public static class GerenciadorFuncionariosApp extends Application {
        private static GerenciadorFuncionarios gerenciador;

        public GerenciadorFuncionariosApp() {
        }

        @Override
        public void start(Stage primaryStage) {
            gerenciador = new GerenciadorFuncionarios();

            Button btnExibirFuncionarios = new Button("Exibir Funcionários");
            btnExibirFuncionarios.setOnAction(e -> exibirFuncionarios());

            Button btnEditarFuncionario = new Button("Editar Funcionário");
            btnEditarFuncionario.setOnAction(e -> editarFuncionario());

            Button btnAdicionarFuncionario = new Button("Adicionar Funcionário");
            btnAdicionarFuncionario.setOnAction(e -> adicionarFuncionario());

            Button btnExcluirFuncionario = new Button("Excluir Funcionário");
            btnExcluirFuncionario.setOnAction(e -> excluirFuncionario());

            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(
                    new Label("Bem-vindo ao Sistema de Gerenciamento de Funcionários"),
                    btnExibirFuncionarios,
                    btnEditarFuncionario,
                    btnAdicionarFuncionario,
                    btnExcluirFuncionario
            );

            Scene scene = new Scene(vbox, 400, 300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gerenciador de Funcionários");
            primaryStage.show();
        }

        private void exibirFuncionarios() {
            // Lógica para exibir os funcionários
            List<Funcionario> funcionarios = gerenciador.getFuncionarios();
            if (funcionarios.isEmpty()) {
                exibirAlertaInformacao("Lista Vazia", "Não há funcionários cadastrados.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Lista de Funcionários:\n");
                for (Funcionario funcionario : funcionarios) {
                    sb.append(funcionario).append("\n");
                }
                exibirAlertaInformacao("Lista de Funcionários", sb.toString());
            }
        }

        private void editarFuncionario() {
            // Lógica para editar o funcionário
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Editar Funcionário");
            dialog.setHeaderText(null);
            dialog.setContentText("Digite o ID do funcionário:");
        
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(idStr -> {
                try {
                    int idFuncionario = Integer.parseInt(idStr);
                    Funcionario funcionarioParaEditar = null;
                    for (Funcionario funcionario : gerenciador.getFuncionarios()) {
                        if (funcionario.getId() == idFuncionario) {
                            funcionarioParaEditar = funcionario;
                            break;
                        }
                    }
                    if (funcionarioParaEditar != null) {
                        // Exibir informações atuais do funcionário
                        Alert informacoesAtuais = new Alert(AlertType.INFORMATION);
                        informacoesAtuais.setTitle("Informações Atuais do Funcionário");
                        informacoesAtuais.setHeaderText(null);
                        informacoesAtuais.setContentText(funcionarioParaEditar.toString());
                        informacoesAtuais.showAndWait();
        
                        // Exibir caixas de diálogo para solicitar os novos valores
                        TextInputDialog nomeDialog = new TextInputDialog(funcionarioParaEditar.getNome());
                        nomeDialog.setTitle("Editar Nome");
                        nomeDialog.setHeaderText(null);
                        nomeDialog.setContentText("Novo Nome:");
        
                        TextInputDialog sobrenomeDialog = new TextInputDialog(funcionarioParaEditar.getSobrenome());
                        sobrenomeDialog.setTitle("Editar Sobrenome");
                        sobrenomeDialog.setHeaderText(null);
                        sobrenomeDialog.setContentText("Novo Sobrenome:");
                        
        
                        TextInputDialog idadeDialog = new TextInputDialog(String.valueOf(funcionarioParaEditar.getIdade()));
                        idadeDialog.setTitle("Editar Idade");
                        idadeDialog.setHeaderText(null);
                        idadeDialog.setContentText("Nova Idade:");
        
                        TextInputDialog cargoDialog = new TextInputDialog(funcionarioParaEditar.getCargo());
                        cargoDialog.setTitle("Editar Cargo");
                        cargoDialog.setHeaderText(null);
                        cargoDialog.setContentText("Novo Cargo:");
        
                        TextInputDialog salarioDialog = new TextInputDialog(String.valueOf(funcionarioParaEditar.getSalario()));
                        salarioDialog.setTitle("Editar Salário");
                        salarioDialog.setHeaderText(null);
                        salarioDialog.setContentText("Novo Salário:");
        
                        TextInputDialog filialDialog = new TextInputDialog(funcionarioParaEditar.getFilial());
                        filialDialog.setTitle("Editar Filial");
                        filialDialog.setHeaderText(null);
                        filialDialog.setContentText("Nova Filial:");
        
                        Optional<String> novoNome = nomeDialog.showAndWait();
                        Optional<String> novoSobrenome = sobrenomeDialog.showAndWait();
                        Optional<String> novaIdade = idadeDialog.showAndWait();
                        Optional<String> novoCargo = cargoDialog.showAndWait();
                        Optional<String> novoSalario = salarioDialog.showAndWait();
                        Optional<String> novaFilial = filialDialog.showAndWait();
        
                        final Funcionario finalFuncionarioParaEditar = funcionarioParaEditar; // Variável final
                        novaIdade.ifPresent(age -> finalFuncionarioParaEditar.setIdade(Integer.parseInt(age)));
                        novoSalario.ifPresent(sal -> finalFuncionarioParaEditar.setSalario(Double.parseDouble(sal)));
        
                        // Mensagem de confirmação
                        exibirAlertaInformacao("Edição Concluída", "As informações do funcionário foram atualizadas com sucesso.");
                    } else {
                        exibirAlertaErro("Funcionário Não Encontrado", "Não foi encontrado nenhum funcionário com o ID informado.");
                    }
                } catch (NumberFormatException ex) {
                    exibirAlertaErro("ID Inválido", "Por favor, insira um ID válido (número inteiro).");
                }
            });
        }

        private void adicionarFuncionario() {
            // Criar caixas de diálogo para solicitar informações do novo funcionário
            TextInputDialog nomeDialog = new TextInputDialog();
            nomeDialog.setTitle("Adicionar Novo Funcionário");
            nomeDialog.setHeaderText(null);
            nomeDialog.setContentText("Nome:");

            TextInputDialog sobrenomeDialog = new TextInputDialog();
            sobrenomeDialog.setTitle("Adicionar Novo Funcionário");
            sobrenomeDialog.setHeaderText(null);
            sobrenomeDialog.setContentText("Sobrenome:");

            TextInputDialog idadeDialog = new TextInputDialog();
            idadeDialog.setTitle("Adicionar Novo Funcionário");
            idadeDialog.setHeaderText(null);
            idadeDialog.setContentText("Idade:");

            TextInputDialog cargoDialog = new TextInputDialog();
            cargoDialog.setTitle("Adicionar Novo Funcionário");
            cargoDialog.setHeaderText(null);
            cargoDialog.setContentText("Cargo:");

            TextInputDialog salarioDialog = new TextInputDialog();
            salarioDialog.setTitle("Adicionar Novo Funcionário");
            salarioDialog.setHeaderText(null);
            salarioDialog.setContentText("Salário:");

            TextInputDialog filialDialog = new TextInputDialog();
            filialDialog.setTitle("Adicionar Novo Funcionário");
            filialDialog.setHeaderText(null);
            filialDialog.setContentText("Filial:");

            Optional<String> nome = nomeDialog.showAndWait();
            Optional<String> sobrenome = sobrenomeDialog.showAndWait();
            Optional<String> idadeStr = idadeDialog.showAndWait();
            Optional<String> cargo = cargoDialog.showAndWait();
            Optional<String> salarioStr = salarioDialog.showAndWait();
            Optional<String> filial = filialDialog.showAndWait();

            // Verificar se todas as informações foram inseridas
            if (nome.isPresent() && sobrenome.isPresent() && idadeStr.isPresent() &&
                    cargo.isPresent() && salarioStr.isPresent() && filial.isPresent()) {
                try {
                    // Converter os valores para os tipos adequados
                    String nomeFuncionario = nome.get();
                    String sobrenomeFuncionario = sobrenome.get();
                    int idadeFuncionario = Integer.parseInt(idadeStr.get());
                    double salarioFuncionario = Double.parseDouble(salarioStr.get());

                    // Gerar um novo ID para o funcionário
                    int novoId = gerenciador.getFuncionarios().size() + 1;

                    // Criar um novo funcionário e adicioná-lo à lista
                    Funcionario novoFuncionario = new Funcionario(novoId, nomeFuncionario, sobrenomeFuncionario,
                            idadeFuncionario, cargo.get(), salarioFuncionario, filial.get());
                    gerenciador.adicionarFuncionario(novoFuncionario);

                    // Mensagem de confirmação
                    exibirAlertaInformacao("Funcionário Adicionado", "Novo funcionário adicionado com sucesso!");
                } catch (NumberFormatException ex) {
                    exibirAlertaErro("Valor Inválido", "Por favor, insira um valor válido para idade e salário.");
                }
            } else {
                exibirAlertaErro("Informações Incompletas", "Por favor, preencha todas as informações para adicionar um novo funcionário.");
            }
        }

        private void excluirFuncionario() {
            // Solicitar o ID do funcionário a ser excluído
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Excluir Funcionário");
            dialog.setHeaderText(null);
            dialog.setContentText("Digite o ID do funcionário:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(idStr -> {
                try {
                    int idFuncionario = Integer.parseInt(idStr);
                    // Verificar se o ID inserido é válido
                    if (idFuncionario > 0 && idFuncionario <= gerenciador.getFuncionarios().size()) {
                        // Obter o funcionário com base no ID
                        Funcionario funcionario = gerenciador.getFuncionarioPorId(idFuncionario);
                        if (funcionario != null) {
                            // Mostrar todas as informações do funcionário
                            String informacoesFuncionario = funcionario.toString();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmação de Exclusão");
                            alert.setHeaderText("Você tem certeza que deseja excluir este funcionário?");
                            alert.setContentText(informacoesFuncionario);

                            // Adicionar botões de "Sim" e "Não"
                            ButtonType buttonTypeSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
                            ButtonType buttonTypeNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
                            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao);

                            // Obter a resposta do usuário
                            Optional<ButtonType> resultado = alert.showAndWait();
                            if (resultado.isPresent() && resultado.get() == buttonTypeSim) {
                                // Excluir o funcionário da lista
                                gerenciador.removerFuncionario(idFuncionario);
                                exibirAlertaInformacao("Funcionário Excluído", "O funcionário foi excluído com sucesso!");
                            } else {
                                // O usuário optou por não excluir o funcionário
                                exibirAlertaInformacao("Exclusão Cancelada", "Nenhuma alteração foi feita.");
                            }
                        } else {
                            exibirAlertaErro("Funcionário Não Encontrado", "Não foi encontrado um funcionário com o ID especificado.");
                        }
                    } else {
                        exibirAlertaErro("ID Inválido", "Por favor, insira um ID válido (número inteiro positivo).");
                    }
                } catch (NumberFormatException ex) {
                    exibirAlertaErro("ID Inválido", "Por favor, insira um ID válido (número inteiro).");
                }
            });
        }

        // Métodos auxiliares para exibir caixas de diálogo
        private void exibirAlertaErro(String titulo, String mensagem) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensagem);
            alert.showAndWait();
        }

        private void exibirAlertaInformacao(String titulo, String mensagem) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensagem);
            alert.showAndWait();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}