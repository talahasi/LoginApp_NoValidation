package dao;

import android.content.ContentValues;
import android.content.Context;


import java.util.ArrayList;

import domain.Usuario;

public class DaoUsuarios {
    private DaoAdapter daoAdapter;
    private Context context;

    public DaoUsuarios(Context context) {
        daoAdapter = new DaoAdapter(context);
    }

    public long insert(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("sobrenome", usuario.getSobrenome());
        values.put("email", usuario.getEmail());
        values.put("usuario", usuario.getUsuario());
        values.put("senha", usuario.getSenha());
        values.put("sexo", usuario.getSexo());


        long result = daoAdapter.queryInsertLastId("usuario", values);

        return result;
    }

    public boolean update(Usuario usuario) {
        Object[] args = {
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getUsuario(),
                usuario.getSenha(),
                usuario.getSexo(),
                usuario.getId()
        };

        boolean result = daoAdapter.queryExecute(
                "UPDATE usuario SET " +
                        "nome = ?, " +
                        "sobrenome = ?, " +
                        "email = ?, " +
                        "usuario = ?," +
                        "senha = ?," +
                        "sexo = ? " +
                        "WHERE id = ?;", args);

        return result;

    }

    public boolean delete(long id) {
        Object[] args = {id};
        boolean result = daoAdapter.queryExecute("DELETE FROM usuario WHERE id = ?", args);

        return result;
    }

    public ArrayList<Usuario> getTodos() {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ObjetoBanco ob = daoAdapter.queryConsulta("SELECT * FROM usuario ORDER BY nome ASC", null);

        if (ob != null) {
            for (int i = 0; i < ob.size(); i++) {
                Usuario usuario = new Usuario();
                usuario.setId(ob.getLong(i, "id"));
                usuario.setNome(ob.getString(i, "nome"));
                usuario.setSobrenome(ob.getString(i, "sobrenome"));
                usuario.setEmail(ob.getString(i, "email"));
                usuario.setUsuario(ob.getString(i, "usuario"));
                usuario.setSenha(ob.getString(i, "senha"));
                usuario.setSexo(ob.getString(i, "sexo"));


                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    public Usuario getUsuario(long id) {
        String[] args = {String.valueOf(id)};
        ObjetoBanco ob = daoAdapter.queryConsulta("SELECT * FROM usuario WHERE id = ?", args);

        Usuario usuario = null;
        if (ob != null) {
            usuario = new Usuario();
            usuario.setId(ob.getLong(0, "id"));
            usuario.setNome(ob.getString(0, "nome"));
            usuario.setSobrenome(ob.getString(0, "sobrenome"));
            usuario.setEmail(ob.getString(0, "email"));
            usuario.setUsuario(ob.getString(0, "usuario"));
            usuario.setSenha(ob.getString(0, "senha"));
            usuario.setSexo(ob.getString(0, "sexo"));

        }

        return usuario;
    }


}