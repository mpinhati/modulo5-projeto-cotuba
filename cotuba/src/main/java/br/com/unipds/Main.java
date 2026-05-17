package br.com.unipds;


import java.nio.file.Path;
import java.util.List;
import nl.siegmann.epublib.domain.*;

public class Main {

    void main(String[] args) {
        int exitCode = executar(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    int executar(String[] args) {
        boolean modoVerboso = true;

        try{

            MainCLI leitor = new MainCLI();
        leitor.ler(args);


        Path diretorioDosMD = leitor.getDiretorioDosMD();
        String formato = leitor.getFormato();
        Path arquivoDeSaida = leitor.getArquivoDeSaida();
        modoVerboso = leitor.isModoVerboso();


        var rendermd = new RenderMD();
        List<String> htmls = rendermd.render(diretorioDosMD);

            if ("pdf".equals(formato)) {
                var gerador_pdf = new GeradorPDF();
                gerador_pdf.gerar(htmls,arquivoDeSaida);


            } else if ("epub".equals(formato)) {
                var gerador_epub = new GeradorEPUB();
                gerador_epub.gerar(htmls,arquivoDeSaida);

            } else {
                throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
            }

            System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
            return 0;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (modoVerboso) {
                System.err.println();
                ex.printStackTrace();
            }
            return 1;
        }
    }

}