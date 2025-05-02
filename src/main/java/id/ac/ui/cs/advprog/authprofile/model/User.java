package id.ac.ui.cs.advprog.authprofile.model;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;
    private String phone;
    private String bio;

    // Constructors, Getters, Setters
}
