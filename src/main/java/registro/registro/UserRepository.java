/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package registro.registro;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author elvbr
 */
public interface UserRepository extends JpaRepository<User, Long>{
     User findByEmail(String email);
}
