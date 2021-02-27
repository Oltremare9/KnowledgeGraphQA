package cn.edu.nju.kg_qa.domain.dto;

/**
 * Description: <br/>
 * date: 2021/2/18 23:32<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class AuthorBean {
        /**
         * 系统号 : 000180397
         * 名称标目 : ["沈越 (1952~)","shen yue"]
         * 标目附注 : ["北京师范大学教授，曾任经济与工商管理学院常务副院长、党委书记，兼任中华外国经济学说研究会学术委员会副主任、北京市经济学总会副会长、北京市外国经济学说研究会副会长。籍贯江苏溧阳，出生地四川成都。研究领域为中国经济改革与发展、经济体制比较、德国经济学说。主编有《计划经济向市场经济稳定转轨研究》等。","四川成都 籍贯：江苏溧阳","shenyue@bnu.edu.cn","经济学","北京师范大学"]
         * 单纯参照 : Shen Yue (1952~)
         * 更新标志 : N
         */


        private String 系统号;
        private String 单纯参照;
        private String 更新标志;
        private String 名称标目;
        private String 标目附注;

        public String get系统号() {
            return 系统号;
        }

        public void set系统号(String 系统号) {
            this.系统号 = 系统号;
        }

        public String get单纯参照() {
            return 单纯参照;
        }

        public void set单纯参照(String 单纯参照) {
            this.单纯参照 = 单纯参照;
        }

        public String get更新标志() {
            return 更新标志;
        }

        public void set更新标志(String 更新标志) {
            this.更新标志 = 更新标志;
        }

        public String get名称标目() {
            return 名称标目;
        }

        public void set名称标目(String 名称标目) {
            this.名称标目 = 名称标目;
        }

        public String get标目附注() {
            return 标目附注;
        }

        public void set标目附注(String 标目附注) {
            this.标目附注 = 标目附注;
        }
}
