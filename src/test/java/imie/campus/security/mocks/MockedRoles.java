package imie.campus.security.mocks;

import imie.campus.security.model.RoleTemplate;

/**
 * Classe contenant des faux rôles pour les tests relatifs
 *   à leur gestion
 * @author Fabien
 */
@SuppressWarnings("unused")
public class MockedRoles {
    /**
     * Rôles de test
     */
    public static RoleTemplate<?> reader() { return new MockRole<>("READER"); }
    public static RoleTemplate<?> writer() { return new MockRole<>("WRITER", reader()); }
    public static RoleTemplate<?> executor() { return new MockRole<>("EXECUTOR", writer()); }

    public static RoleTemplate<?> user() { return new MockRole<>("USER"); }
    public static RoleTemplate<?> moderator() { return new MockRole<>("MODERATOR", user()); }
    public static RoleTemplate<?> admin() { return new MockRole<>("ADMIN", moderator()); }

    /**
     * Classe définissant un rôle de base
     * @param <A> Le type d'informations additionnelles
     * @author Fabien
     */
    public static class MockRole<A> implements RoleTemplate<A> {
        /**
         * La clé du rôle
         */
        private final String key;

        /**
         * Le parent du rôle
         */
        private RoleTemplate<A> parent;

        /**
         * Construit un rôle de base sans parent
         * @param key La clé du rôle
         */
        public MockRole(String key) {
            this.key = key;
        }

        /**
         * Construit un rôle de base avec parent
         * @param key La clé du rôle
         * @param parent Le rôle parent
         */
        public MockRole(String key, RoleTemplate<A> parent) {
            this.key = key;
            this.parent = parent;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getKey() {
            return key;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public RoleTemplate<A> getParent() {
            return parent;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof MockRole &&
                    ((MockRole) obj).getKey().equals(key);
        }

        @Override
        public String toString() {
            return key;
        }
    }
}
