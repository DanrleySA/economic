package br.edu.unicatolica.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.unicatolica.bo.CategoriaBO;
import br.edu.unicatolica.dao.UsuarioDAO;
import br.edu.unicatolica.entity.Categoria;
import br.edu.unicatolica.filter.CategoriaFilter;
import br.edu.unicatolica.jsf.util.FacesUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@ManagedBean
@ViewScoped
public class ConsultaCategoriaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Categoria categoria;
    private CategoriaFilter categoriaFilter;
    private List<Categoria> categorias;
    private Categoria categoriaSelecionada;

    public ConsultaCategoriaBean() {
        limpar();
        categoriaFilter = new CategoriaFilter();
    }

    @PostConstruct
    public void init() {
        pesquisar();
    }

    public void remover() {
        CategoriaBO.getInstance().remover(categoriaSelecionada);
        categorias.remove(categoriaSelecionada);
        FacesUtil.addInfoMessage("Categoria excluída com sucesso!");
    }

    public void pesquisar() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        categorias = CategoriaBO.getInstance().getCategoriasUsuario(
                categoriaFilter,
                UsuarioDAO.getInstance().getUserPorNome(((User) authentication.getPrincipal()).getUsername()));
    }

    private void limpar() {
        categoria = new Categoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public CategoriaFilter getCategoriaFilter() {
        if (categoriaFilter == null) {
            categoriaFilter = new CategoriaFilter();
        }
        return categoriaFilter;
    }

    public void setCategoriaFilter(CategoriaFilter categoriaFilter) {
        this.categoriaFilter = categoriaFilter;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Categoria getCategoriaSelecionada() {
        return categoriaSelecionada;
    }

    public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
        this.categoriaSelecionada = categoriaSelecionada;
    }

}
